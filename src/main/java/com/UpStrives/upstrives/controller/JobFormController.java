package com.UpStrives.upstrives.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.UpStrives.upstrives.dto.JobFormDto;
import com.UpStrives.upstrives.service.JobFormService;

@RestController
@RequestMapping("/api/jobform")
public class JobFormController {

  @Autowired
  private JobFormService jobFormService;

  @PostMapping("/submit")
  public ResponseEntity<String> submitJobForm(@ModelAttribute JobFormDto jobFormDto) {
    try {
      // Validate the job form data
      jobFormService.validateJobForm(jobFormDto);
      return ResponseEntity.ok("Job form submitted successfully");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Error submitting job form: " + e.getMessage());
    }
  }

}
