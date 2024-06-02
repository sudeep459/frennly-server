package com.frennly.ds.controller;

import com.frennly.ds.model.UserRegistration;
import com.frennly.ds.payload.response.ApiResponse;
import com.frennly.ds.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class RegistrationController {

    @Autowired
    private RegistrationRepository registrationRepository;
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody UserRegistration userRegistration) {
        registrationRepository.save(userRegistration);
        return ResponseEntity.ok(new ApiResponse("Registration Completed", true));
    }
}
