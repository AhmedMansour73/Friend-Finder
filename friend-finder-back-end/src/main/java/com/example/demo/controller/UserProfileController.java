package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.vm.AuthResponseVm;
import com.example.demo.dto.user_profile.UserProfileRequestDTO;
import com.example.demo.dto.user_profile.UserProfileResponseDTO;
import com.example.demo.service.UserProfileService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/profiles")
public class UserProfileController {
	
	private final UserProfileService profileService;

    public UserProfileController(UserProfileService profileService) {
        this.profileService = profileService;
    }

    // 1️⃣ Get all profiles
    @GetMapping
    public ResponseEntity<List<UserProfileResponseDTO>> getAllProfiles() {
        List<UserProfileResponseDTO> profiles = profileService.getAllUserProfile();
        return ResponseEntity.ok(profiles);
    }

    // 2️⃣ Get profile by id
    @GetMapping("/{id}")
    public ResponseEntity<UserProfileResponseDTO> getProfileById(@PathVariable Long id) {
        UserProfileResponseDTO profile = profileService.getUserProfileById(id);
        return ResponseEntity.ok(profile);
    }

    

}
