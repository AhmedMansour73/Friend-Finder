package com.example.demo.controller.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.vm.AuthResponseVm;
import com.example.demo.dto.user_account.UserAccountRequestDTO;
import com.example.demo.dto.user_profile.UserProfileRequestDTO;
import com.example.demo.service.security.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	
	private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    
 // Create profile
    @PostMapping("/signup")
    public ResponseEntity<AuthResponseVm> createProfile(@RequestBody @Valid UserProfileRequestDTO requestDto) {
        AuthResponseVm createdProfile = authService.createUserProfile(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
    }

    // ------------------ LOGIN ------------------
    @PostMapping("/login")
    public ResponseEntity<AuthResponseVm> login(@RequestBody @Valid UserAccountRequestDTO requestDto) {
        // Call the service to perform login and generate JWT
        AuthResponseVm response = authService.loginToProfile(requestDto);

        // Return token in response
        return ResponseEntity.ok(response);
    }

}
