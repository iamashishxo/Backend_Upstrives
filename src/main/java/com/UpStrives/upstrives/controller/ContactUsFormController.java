package com.UpStrives.upstrives.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.UpStrives.upstrives.dto.ContactUsFormDto;
import com.UpStrives.upstrives.entity.ContactUsFormEntity;
import com.UpStrives.upstrives.repository.ContactUsFormRepository;

@RestController
@RequestMapping("/api/contactus")
public class ContactUsFormController {

  @Autowired
  private ContactUsFormRepository contactUsFormRepository;

  @PostMapping("/submit")
  public ResponseEntity<String> submitContactUsForm(@ModelAttribute ContactUsFormDto contactUsFormDto) {
    // Convert DTO to Entity
    try {
      ContactUsFormEntity contactUsFormEntity = new ContactUsFormEntity();
      contactUsFormEntity.setName(contactUsFormDto.getName());
      contactUsFormEntity.setEmail(contactUsFormDto.getEmail());
      contactUsFormEntity.setSubject_message(contactUsFormDto.getSubject_message());
      contactUsFormEntity.setMessage(contactUsFormDto.getMessage());

      // Save the entity using the repository
      contactUsFormRepository.save(contactUsFormEntity);

      return ResponseEntity.ok("Contact Us form submitted successfully");

    } catch (Exception e) {
      // Handle exceptions and return an error response
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Error submitting Contact Us form: " + e.getMessage());
    }
  }

}
