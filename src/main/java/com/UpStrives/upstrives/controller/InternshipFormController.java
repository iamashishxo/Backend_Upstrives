package com.UpStrives.upstrives.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.UpStrives.upstrives.dto.InternshipFormDto;
import com.UpStrives.upstrives.entity.InternshipFormEntity;
import com.UpStrives.upstrives.repository.InternshipFormRepository;

@RestController
@RequestMapping("/api/internship")
public class InternshipFormController {

  @Autowired
  private InternshipFormRepository internshipFormRepository;

  @PostMapping("/submit")
  public ResponseEntity<String> submitInternshipForm(@ModelAttribute InternshipFormDto internshipFormDto) {
    try {
      InternshipFormEntity internshipFormEntity = new InternshipFormEntity();
      internshipFormEntity.setName(internshipFormDto.getName());
      internshipFormEntity.setEmail(internshipFormDto.getEmail());
      internshipFormEntity.setPhone(internshipFormDto.getPhone());
      internshipFormEntity.setProgram(internshipFormDto.getProgram());
      internshipFormEntity.setTime_duration(internshipFormDto.getTime_duration());

      MultipartFile resume = internshipFormDto.getResume();
      if (resume != null && !resume.isEmpty()) {
        internshipFormEntity.setResume(resume.getBytes());
        internshipFormEntity.setResumefilename(resume.getOriginalFilename());
      }

      internshipFormRepository.save(internshipFormEntity);
      return ResponseEntity.ok("Internship form submitted successfully");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Error submitting internship form: " + e.getMessage());
    }
  }
}
