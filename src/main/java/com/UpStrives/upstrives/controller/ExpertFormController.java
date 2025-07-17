package com.UpStrives.upstrives.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.UpStrives.upstrives.dto.ExpertFormDto;
import com.UpStrives.upstrives.entity.ExpertFormEntity;
import com.UpStrives.upstrives.repository.EnityformRepository;

@RestController
@RequestMapping("/api/expertform")
public class ExpertFormController {
  @Autowired
  private EnityformRepository enityformRepository;

  @PostMapping("/submit")
  public ResponseEntity<String> saveExpertForm(@ModelAttribute ExpertFormDto expertFormDto) {
    try {
      ExpertFormEntity expertFormEntity = new ExpertFormEntity();
      expertFormEntity.setName(expertFormDto.getName());
      expertFormEntity.setEmail(expertFormDto.getEmail());
      expertFormEntity.setPhone(expertFormDto.getPhone());
      expertFormEntity.setDegree(expertFormDto.getDegree());
      expertFormEntity.setDomain(expertFormDto.getDomain());

      MultipartFile resume = expertFormDto.getResume();
      MultipartFile payslip = expertFormDto.getPayslip();
      if (payslip != null && !payslip.isEmpty()) {
        if (resume != null && !resume.isEmpty()) {
          expertFormEntity.setResume(resume.getBytes());
          expertFormEntity.setPayslip(payslip.getBytes());
        }
      }
      enityformRepository.save(expertFormEntity);
      return ResponseEntity.ok("Internship form submitted successfully");
      // Return a success response
    } catch (Exception e) {
      // TODO: handle exception
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Error submitting internship form: ");
  }

}
