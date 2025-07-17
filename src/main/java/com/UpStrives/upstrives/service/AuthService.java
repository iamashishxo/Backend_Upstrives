package com.UpStrives.upstrives.service;


import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.UpStrives.upstrives.dto.ForgotPasswordRequest;
import com.UpStrives.upstrives.dto.LoginRequest;
import com.UpStrives.upstrives.dto.RegisterUserRequest;
import com.UpStrives.upstrives.dto.UpdatePasswordRequest;
import com.UpStrives.upstrives.entity.User;
import com.UpStrives.upstrives.repository.UserRepository;


@Service
public class AuthService {
	
	
	public enum LoginResult {
	    SUCCESS,
	    INVALID_EMAIL,
	    INVALID_PASSWORD,
	    INVALID_EMAIL_AND_PASSWORD
	}

  
	private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;           

    public AuthService(UserRepository userRepository,
                       BCryptPasswordEncoder passwordEncoder) {     
        this.userRepository = userRepository;
        this.encoder = passwordEncoder;
    }

public String register(RegisterUserRequest request) {

      if (userRepository.findByEmail(request.getEmail()).isPresent()) {
          return "Email already registered.";
      }

      User user = new User();
      user.setEmail(request.getEmail());
      user.setPassword(encoder.encode(request.getPassword())); 

      userRepository.save(user);
      return "User registered successfully.";
  }

//public String login(LoginRequest loginRequest) {
//
//    Optional<User> userOpt = userRepository.findByEmail(loginRequest.getEmail());
//    if (userOpt.isPresent() &&
//    		encoder.matches(loginRequest.getPassword(), userOpt.get().getPassword())) {
//        return "Login successful";
//    }
//    return "Invalid email or password";
//}


public LoginResult login(LoginRequest loginRequest) {

    Optional<User> userOpt = userRepository.findByEmail(loginRequest.getEmail());

    if (userOpt.isEmpty()) {
        
        return LoginResult.INVALID_EMAIL_AND_PASSWORD;
    }

    if (!encoder.matches(loginRequest.getPassword(),
                         userOpt.get().getPassword())) {
        return LoginResult.INVALID_PASSWORD;    
    }

    return LoginResult.SUCCESS;               
}

public String forgotPassword(ForgotPasswordRequest request) {
    return userRepository.findByEmail(request.getEmail()).isPresent()
        ? "Email found. Proceed to update password."
        : "Email not registered.";
}

public String updatePassword(UpdatePasswordRequest request) {

    Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

    if (userOpt.isPresent()) {
        User user = userOpt.get();
        String hashed = encoder.encode(request.getNewPassword());
        user.setPassword(hashed);

        userRepository.save(user);
        return "Password updated successfully.";
    }
    return "Email not registered.";
}
}
