package com.example.demo.service.security;

import com.example.demo.controller.vm.AuthResponseVm;
import com.example.demo.dto.user_account.UserAccountRequestDTO;
import com.example.demo.dto.user_profile.UserProfileRequestDTO;

public interface AuthService {
	
	AuthResponseVm createUserProfile(UserProfileRequestDTO requestDto);
	
	AuthResponseVm loginToProfile(UserAccountRequestDTO reqAccountDto);
	

}
