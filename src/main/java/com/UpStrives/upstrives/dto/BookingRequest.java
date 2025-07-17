package com.UpStrives.upstrives.dto;

import lombok.Data;

@Data
public class BookingRequest {
	private String email;     // NEW
    private String password;  // NEW
    private String date;  // ISO yyyy-MM-dd
    private String time;  // HH:mm 24‑hour
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
    
}
