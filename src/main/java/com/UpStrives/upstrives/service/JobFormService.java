package com.UpStrives.upstrives.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.UpStrives.upstrives.dto.JobFormDto;
import com.UpStrives.upstrives.entity.JobFormEntity;
import com.UpStrives.upstrives.repository.JobFormRepository;

@Service
public class JobFormService {

  @Autowired
  private JobFormRepository jobFormRepository;

  @Autowired
  private JavaMailSender mailSender;


  public String validateJobForm(JobFormDto jobFormDto) {
    JobFormEntity jobFormEntity = new JobFormEntity();
    jobFormEntity.setName(jobFormDto.getName());
    jobFormEntity.setEmail(jobFormDto.getEmail());
    jobFormEntity.setPhoneNumber(jobFormDto.getPhoneNumber());

    try {
      jobFormEntity.setResume(jobFormDto.getResume().getBytes());
    } catch (Exception e) {
      throw new RuntimeException("Failed to process resume file", e);
    }

//    try {
//      jobFormEntity.setResume(jobFormDto.getResume().getBytes());
//    } catch (Exception e) {
//      throw new RuntimeException("Failed to process resume file", e);
//    }
    try {
        if (jobFormDto.getResume() != null && !jobFormDto.getResume().isEmpty()) {
          jobFormEntity.setResume(jobFormDto.getResume().getBytes());
        } else {
          throw new RuntimeException("Resume file is required");
        }
      } catch (Exception e) {
        throw new RuntimeException("Failed to process resume file", e);
      }

    jobFormEntity.setCoverletter(jobFormDto.getCoverletter());

    jobFormRepository.save(jobFormEntity);


    sendConfirmationEmail(jobFormDto.getEmail());

    return "Job form submitted successfully";

  }

  
    private void sendConfirmationEmail(String email) {
    String subject = "Job Application Confirmation";
    String text =
    "Thank you for applying for the job. We will review your application and get back to you soon.\n\nBest regards,\nDevLoomX Team" ;
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(email);
    message.setSubject(subject);
    message.setText(text);
    message.setFrom("upstrives@gmail.com");
    
    mailSender.send(message);
    System.out.println("Confirmation email sent to: " + email);
    }
   


}
