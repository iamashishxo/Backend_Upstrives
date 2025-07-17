package com.UpStrives.upstrives.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CalendarDayResponse {
    private String date;     // yyyy‑MM‑dd
    private boolean bookable;
    
    
	public CalendarDayResponse(String date, boolean bookable) {
		super();
		this.date = date;
		this.bookable = bookable;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public boolean isBookable() {
		return bookable;
	}
	public void setBookable(boolean bookable) {
		this.bookable = bookable;
	}
    
}