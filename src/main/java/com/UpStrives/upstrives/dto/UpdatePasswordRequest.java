package com.UpStrives.upstrives.dto;


import lombok.Data;

@Data
public class UpdatePasswordRequest {
  private String email;
  private String newPassword;
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getNewPassword() {
	return newPassword;
}
public void setNewPassword(String newPassword) {
	this.newPassword = newPassword;
}
  
}