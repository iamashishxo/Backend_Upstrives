package com.UpStrives.upstrives.service;

import com.UpStrives.upstrives.dto.CalendarDayResponse;
import com.UpStrives.upstrives.dto.CalendarDaySlotsResponse;
import com.UpStrives.upstrives.entity.ExpertSessionSlot;
import com.UpStrives.upstrives.entity.User;
import com.UpStrives.upstrives.repository.ExpertSessionSlotRepo;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class SlotBookingService {

    private final ExpertSessionSlotRepo repo;
    private final JavaMailSender mail;

    public SlotBookingService(ExpertSessionSlotRepo repo, JavaMailSender mail) {
        super();
        this.repo = repo;
        this.mail = mail;
    }

    /* ---- date/time window constants ---- */
    private static final LocalTime START_AM = LocalTime.of(9, 30);
    private static final LocalTime LUNCH_START = LocalTime.of(13, 0);
    private static final LocalTime LUNCH_END = LocalTime.of(16, 0);
    private static final LocalTime END_PM = LocalTime.of(21, 30);

    /* -------- public API -------- */

    public List<LocalTime> freeSlots(LocalDate date) {
        return repo.findBySessionDateAndStatus(date, ExpertSessionSlot.Status.FREE)
                .stream()
                .map(ExpertSessionSlot::getStartTime)
                .sorted()
                .toList();
    }

    public List<CalendarDayResponse> calendar(int horizonDays) {
        LocalDate today = LocalDate.now();
        return IntStream.rangeClosed(0, horizonDays - 1)
                .mapToObj(i -> {
                    LocalDate d = today.plusDays(i);
                    boolean bookable = repo.countBySessionDateAndStatus(d, ExpertSessionSlot.Status.FREE) > 0;
                    return new CalendarDayResponse(d.toString(), bookable);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void book(LocalDate date, LocalTime time, User user) {
        ExpertSessionSlot slot = repo.findBySessionDateAndStartTime(date, time)
                .orElseThrow(() -> new IllegalArgumentException("Slot does not exist"));

        if (slot.getStatus() == ExpertSessionSlot.Status.BOOKED) {
            throw new IllegalStateException("Slot already booked");
        }

        slot.setStatus(ExpertSessionSlot.Status.BOOKED);
        slot.setUser(user);
        slot.setMeetingLink("TBD-meeting-link");
        repo.save(slot);

        sendMail(user.getEmail(), date, time);
    }

    private void sendMail(String to, LocalDate d, LocalTime t) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject("Expert Session Confirmation");
        msg.setText("Your expert session is confirmed on " + d + " at " + t + ".\nMeeting link will be shared soon.");
        mail.send(msg);
    }

    public List<CalendarDaySlotsResponse> calendarWithSlots(int horizonDays) {

        LocalDate today = LocalDate.now();

        /* full fixed time list once */
        List<LocalTime> FULL_LIST = new ArrayList<>();
        for (LocalTime t = LocalTime.of(9, 30); !t.isAfter(LocalTime.of(21, 30)); t = t.plusMinutes(30)) {

            if (t.isBefore(LocalTime.of(13, 0)) ||
                    !t.isBefore(LocalTime.of(16, 0))) {
                FULL_LIST.add(t);
            }
        }

        return IntStream.rangeClosed(0, horizonDays - 1)
                .mapToObj(i -> {
                    LocalDate date = today.plusDays(i);

                    /* start every slot as free (1) */
                    Map<String, Integer> map = FULL_LIST.stream()
                            .collect(Collectors.toMap(
                                    LocalTime::toString,
                                    t -> 1,
                                    (a, b) -> a,
                                    LinkedHashMap::new)); // keep order

                    /* set to 0 if booked */
                    repo.findBySessionDate(date)
                            .forEach(slot -> map.put(slot.getStartTime().toString(),
                                    slot.getStatus() == ExpertSessionSlot.Status.FREE ? 1 : 0));

                    return new CalendarDaySlotsResponse(date.toString(), map);
                })
                .toList();
    }

    // alternatative to delete records from db
    @PostConstruct
    public void cleanOldSlotsOnStartup() {
        repo.deleteOldUnbookedSlots(LocalDate.now());
        System.out.println("Old unbooked slots removed from Db ");
    }
    // Schedulet to delete records
    // @Scheduled(cron = "0 0 1 * * ?") // Runs daily at 1 AM
    // public void cleanOldSlotsDaily() {
    // repo.deleteOldUnbookedSlots(LocalDate.now());
    // System.out.println("Old unbooked slots cleaned at 1 AM");
    // }
}
