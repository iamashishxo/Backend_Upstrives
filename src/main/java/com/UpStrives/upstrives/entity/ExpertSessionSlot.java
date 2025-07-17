package com.UpStrives.upstrives.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Entity
@Table(name = "expert_session_slot", uniqueConstraints = @UniqueConstraint(columnNames = {"session_date", "start_time"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpertSessionSlot {

    public enum Status { FREE, BOOKED }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate sessionDate;
    private LocalTime startTime;

    @Enumerated(EnumType.STRING)
    private Status status = Status.FREE;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String meetingLink;

    private LocalDateTime createdAt = LocalDateTime.now();
    
    public ExpertSessionSlot() {          
    }

    //  Add this constructor for use in scheduler
    public ExpertSessionSlot(LocalDate sessionDate, LocalTime startTime, Status status) {
        this.sessionDate = sessionDate;
        this.startTime = startTime;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

    // Or this one if you're passing null for id, user, meetingLink
    public ExpertSessionSlot(Long id, LocalDate sessionDate, LocalTime startTime, Status status, User user, String meetingLink) {
        this.id = id;
        this.sessionDate = sessionDate;
        this.startTime = startTime;
        this.status = status;
        this.user = user;
        this.meetingLink = meetingLink;
        this.createdAt = LocalDateTime.now();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getSessionDate() {
		return sessionDate;
	}

	public void setSessionDate(LocalDate sessionDate) {
		this.sessionDate = sessionDate;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getMeetingLink() {
		return meetingLink;
	}

	public void setMeetingLink(String meetingLink) {
		this.meetingLink = meetingLink;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
    
    
}