package com.UpStrives.upstrives.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InternshipFormDto {

	private String name;

	private String phone;

	private String email;

	private String program; // e.g. Web Development, AI/ML, etc.

	private MultipartFile resume;

	private String resumefilename; // Optional, if you want to store the filename separately

	private int time_duration; // Duration of the internship in weeks or months

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public MultipartFile getResume() {
		return resume;
	}

	public void setResume(MultipartFile resume) {
		this.resume = resume;
	}

	public String getResumefilename() {
		return resumefilename;
	}

	public void setResumefilename(String resumefilename) {
		this.resumefilename = resumefilename;
	}

	public int getTime_duration() {
		return time_duration;
	}

	public void setTime_duration(int time_duration) {
		this.time_duration = time_duration;
	}

}
