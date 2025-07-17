package com.UpStrives.upstrives.config;

import com.UpStrives.upstrives.entity.ExpertSessionSlot;
import com.UpStrives.upstrives.repository.ExpertSessionSlotRepo;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SlotScheduler {

    private final ExpertSessionSlotRepo repo;
    


	public SlotScheduler(ExpertSessionSlotRepo repo) {
		super();
		this.repo = repo;
	}

	private static final LocalTime START_AM    = LocalTime.of(9, 30);
    private static final LocalTime LUNCH_START = LocalTime.of(13, 0);
    private static final LocalTime LUNCH_END   = LocalTime.of(16, 0);
    private static final LocalTime END_PM      = LocalTime.of(21, 30);

    @Value("${booking.horizon-days:14}")
    private int horizonDays;

    @Scheduled(cron = "0 5 0 * * ?")    // every day at 00:05
    public void populateSlots() {
        LocalDate today = LocalDate.now();
        for (int i = 0; i < horizonDays; i++) {
            LocalDate d = today.plusDays(i);
            if (repo.countBySessionDate(d) == 0) {  // day not generated
                repo.saveAll(generateDay(d));
            }
        }
    }
    @PostConstruct
    public void initToday() {
        populateSlots();   
    }

    private List<ExpertSessionSlot> generateDay(LocalDate date) {
        List<ExpertSessionSlot> rows = new ArrayList<>();
        for (LocalTime t = START_AM; !t.isAfter(END_PM); t = t.plusMinutes(30)) {
            if (t.isBefore(LUNCH_START) || !t.isBefore(LUNCH_END)) {
                rows.add(new ExpertSessionSlot(null, date, t, ExpertSessionSlot.Status.FREE, null, null));
            }
        }
        return rows; // 19 rows
    }
}
