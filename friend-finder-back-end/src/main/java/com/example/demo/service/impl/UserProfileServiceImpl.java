package com.example.demo.service.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.controller.vm.AuthResponseVm;
import com.example.demo.dto.user_profile.UserProfileRequestDTO;
import com.example.demo.dto.user_profile.UserProfileResponseDTO;
import com.example.demo.entities.UserProfile;
import com.example.demo.entities.security.Role;
import com.example.demo.entities.security.RoleEnum;
import com.example.demo.entities.security.UserAccount;
import com.example.demo.mapper.UserProfileMapper;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserAccountRepository;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.security.jwt.JwtService;
import com.example.demo.service.UserProfileService;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Service
public class UserProfileServiceImpl implements UserProfileService{

	private final UserProfileRepository profileRepository;
	private final UserAccountRepository accountRepository;
	private final UserProfileMapper profileMapper;
	private final PasswordEncoder passwordEncoder;
	private final RoleRepository roleRepository;
	
	private final JwtService jwtService;
	    

	public UserProfileServiceImpl(UserProfileRepository profileRepository, UserAccountRepository accountRepository,
			                       UserProfileMapper profileMapper , PasswordEncoder passwordEncoder , RoleRepository roleRepository,
			                       JwtService jwtService) {
		this.profileRepository = profileRepository;
		this.accountRepository = accountRepository;
		this.profileMapper = profileMapper;
		this.passwordEncoder=passwordEncoder;
		this.roleRepository=roleRepository;
		this.jwtService=jwtService;
	}

	@Override
	public UserProfileResponseDTO getUserProfileById(Long id) {
		return profileMapper.toResponseDTO( profileRepository.findById(id).get()); 
	}

	@Override
	public List<UserProfileResponseDTO> getAllUserProfile() {
		List<UserProfile> profiles = profileRepository.findAll();
	    return profileMapper.toResponseDTOList(profiles);
	}
		

	@Override
	public UserProfileResponseDTO updateUserProfile(Long id, UserProfileRequestDTO requestDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUserProfile(Long id) {
		// TODO Auto-generated method stub
		
	}

}
