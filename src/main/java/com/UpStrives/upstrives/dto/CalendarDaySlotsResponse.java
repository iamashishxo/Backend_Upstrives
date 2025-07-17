package com.UpStrives.upstrives.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Map;

@Data
@AllArgsConstructor
public class CalendarDaySlotsResponse {
    private String date;                 // yyyy‑MM‑dd
    private Map<String, Integer> slots;  // "09:30" -> 1 (free) / 0 (booked)
    
	public CalendarDaySlotsResponse(String date, Map<String, Integer> slots) {
		super();
		this.date = date;
		this.slots = slots;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Map<String, Integer> getSlots() {
		return slots;
	}
	public void setSlots(Map<String, Integer> slots) {
		this.slots = slots;
	}
    
}
