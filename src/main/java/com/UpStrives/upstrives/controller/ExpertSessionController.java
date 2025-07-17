package com.UpStrives.upstrives.controller;

import com.UpStrives.upstrives.dto.*;
import com.UpStrives.upstrives.entity.User;
import com.UpStrives.upstrives.repository.UserRepository;
import com.UpStrives.upstrives.service.SlotBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class ExpertSessionController {

    private final SlotBookingService service;
    private final UserRepository userRepo;                 
    private final BCryptPasswordEncoder encoder;       
    

	public ExpertSessionController(SlotBookingService service, UserRepository userRepo, BCryptPasswordEncoder encoder) {
		super();
		this.service = service;
		this.userRepo = userRepo;
		this.encoder = encoder;
	}

	@GetMapping("/calendar")
    public List<CalendarDayResponse> calendar(@RequestParam(defaultValue = "14") int days) {
        return service.calendar(days);
    }

//    @GetMapping("/slots")
//    public SlotListResponse slots(@RequestParam String date) {
//        LocalDate d = LocalDate.parse(date);
//        List<String> free = service.freeSlots(d).stream()
//                                   .map(LocalTime::toString)
//                                   .toList();
//        return new SlotListResponse(date, free);
//    }
	
	@GetMapping("/slots")
	public ResponseEntity<?> slots(@RequestParam String date) {
	    LocalDate requestedDate;
	    try {
	        requestedDate = LocalDate.parse(date);
	    } catch (DateTimeParseException e) {
	        return ResponseEntity.badRequest().body("Invalid date format. Use yyyy-MM-dd.");
	    }

	    LocalDate today = LocalDate.now();

	    if (requestedDate.isBefore(today)) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                             .body("Invalid date: You cannot view slots for past dates.");
	    }

	    List<String> free = service.freeSlots(requestedDate)
	                               .stream()
	                               .map(LocalTime::toString)
	                               .toList();

	    return ResponseEntity.ok(new SlotListResponse(date, free));
	}

//    @PostMapping("/book")
//    public ResponseEntity<String> book(@RequestBody BookingRequest req) {
//
//       
//        User user = userRepo.findByEmail(req.getEmail())
//                            .orElse(null);
//
//        if (user == null || !encoder.matches(req.getPassword(), user.getPassword())) {
//            return ResponseEntity
//                    .status(HttpStatus.UNAUTHORIZED)
//                    .body("Invalid credentials");
//        }
//        try {
//        	service.book(
//                    LocalDate.parse(req.getDate()),
//                    LocalTime.parse(req.getTime()),
//                    user);
//
//            return ResponseEntity.status(HttpStatus.CREATED)
//                                 .body("Slot booked for "
//                                       + req.getDate() + " " + req.getTime());
//
//        } catch (IllegalStateException ex) {             
//            return ResponseEntity
//                    .status(HttpStatus.CONFLICT)        
//                    .body("Slot already booked");
//        }
//
//    }
	@PostMapping("/book")
	public ResponseEntity<String> book(@RequestBody BookingRequest req) {

	    User user = userRepo.findByEmail(req.getEmail())
	                        .orElse(null);

	    if (user == null || !encoder.matches(req.getPassword(), user.getPassword())) {
	        return ResponseEntity
	                .status(HttpStatus.UNAUTHORIZED)
	                .body("Invalid credentials");
	    }

	    LocalDate date = LocalDate.parse(req.getDate());
	    LocalTime time = LocalTime.parse(req.getTime());

	    // Check if the date is in the past
	    if (date.isBefore(LocalDate.now()) ||
	       (date.isEqual(LocalDate.now()) && time.isBefore(LocalTime.now()))) {
	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body("Cannot book a past slot. Please select an upcoming date and time.");
	    }

	    try {
	        service.book(date, time, user);

	        return ResponseEntity.status(HttpStatus.CREATED)
	                             .body("Slot booked for "
	                                   + req.getDate() + " " + req.getTime());

	    } catch (IllegalStateException ex) {
	        return ResponseEntity
	                .status(HttpStatus.CONFLICT)
	                .body("Slot already booked");
	    }
	}
    @GetMapping("/calendar-slots")
    public List<CalendarDaySlotsResponse> calendarSlots(
            @RequestParam(defaultValue = "14") int days) {
        return service.calendarWithSlots(days);
    }

}
