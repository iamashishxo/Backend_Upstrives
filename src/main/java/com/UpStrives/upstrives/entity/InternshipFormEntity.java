package com.UpStrives.upstrives.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "InternshipRequestForm")
@Data
public class InternshipFormEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String phone;

	private String email;

	private String program; // e.g. Web Development, AI/ML, etc.

	@Lob
	private byte[] resume;
	private String resumefilename;

	private int time_duration;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public byte[] getResume() {
		return resume;
	}

	public void setResume(byte[] resume) {
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
