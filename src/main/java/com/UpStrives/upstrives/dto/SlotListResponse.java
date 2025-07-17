package com.UpStrives.upstrives.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class SlotListResponse {
    private String date;
    private List<String> freeSlots;
    
    
	public SlotListResponse(String date, List<String> freeSlots) {
		super();
		this.date = date;
		this.freeSlots = freeSlots;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<String> getFreeSlots() {
		return freeSlots;
	}
	public void setFreeSlots(List<String> freeSlots) {
		this.freeSlots = freeSlots;
	}
    
}
