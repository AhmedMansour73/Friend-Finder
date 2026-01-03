package com.example.demo.service;

import java.util.List;

import com.example.demo.controller.vm.AuthResponseVm;
import com.example.demo.dto.user_profile.UserProfileRequestDTO;
import com.example.demo.dto.user_profile.UserProfileResponseDTO;



public interface UserProfileService {

	UserProfileResponseDTO getUserProfileById(Long id);

    List<UserProfileResponseDTO> getAllUserProfile();

    UserProfileResponseDTO updateUserProfile(Long id, UserProfileRequestDTO requestDto);

    void deleteUserProfile(Long id);

}
