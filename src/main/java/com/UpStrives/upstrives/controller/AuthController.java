package com.UpStrives.upstrives.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.UpStrives.upstrives.dto.ForgotPasswordRequest;
import com.UpStrives.upstrives.dto.LoginRequest;
import com.UpStrives.upstrives.dto.RegisterUserRequest;
import com.UpStrives.upstrives.dto.UpdatePasswordRequest;
import com.UpStrives.upstrives.service.AuthService;
import com.UpStrives.upstrives.service.AuthService.LoginResult;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private AuthService authService;
  
  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody RegisterUserRequest request) {
      return ResponseEntity.ok(authService.register(request));
  }

//  @PostMapping("/login")
//  public ResponseEntity<String> login(@RequestBody LoginRequest request) {
//    return ResponseEntity.ok(authService.login(request));
//  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody LoginRequest req) {

      LoginResult result = authService.login(req);

      switch (result) {
          case SUCCESS:
              return ResponseEntity.ok("Login successful");

          case INVALID_PASSWORD:
              return ResponseEntity
                      .status(HttpStatus.UNAUTHORIZED)
                      .body("Invalid password");

          case INVALID_EMAIL_AND_PASSWORD:        
              return ResponseEntity
                      .status(HttpStatus.UNAUTHORIZED)
                      .body("Invalid email and password");

          default:
              return ResponseEntity
                      .status(HttpStatus.INTERNAL_SERVER_ERROR)
                      .body("Unexpected error");
      }
  }


  @PostMapping("/forgot-password")
  public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
    return ResponseEntity.ok(authService.forgotPassword(request));
  }

  @PostMapping("/update-password")
  public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordRequest request) {
    return ResponseEntity.ok(authService.updatePassword(request));
  }
}
