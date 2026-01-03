package com.example.demo.service.security.impl;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.controller.vm.AuthResponseVm;
import com.example.demo.dto.user_account.UserAccountRequestDTO;
import com.example.demo.dto.user_profile.UserProfileRequestDTO;
import com.example.demo.entities.UserProfile;
import com.example.demo.entities.security.Role;
import com.example.demo.entities.security.RoleEnum;
import com.example.demo.entities.security.UserAccount;
import com.example.demo.mapper.UserProfileMapper;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserAccountRepository;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.security.jwt.JwtService;
import com.example.demo.service.security.AuthService;

@Service
public class AuthServiceImpl implements AuthService{
	
	private final UserProfileRepository profileRepository;
	private final UserAccountRepository accountRepository;
	private final RoleRepository roleRepository;
	
	private final UserProfileMapper profileMapper;
	
	private final PasswordEncoder passwordEncoder;
	
	
	private final JwtService jwtService;
	    

	public AuthServiceImpl(UserProfileRepository profileRepository, UserAccountRepository accountRepository,
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
	public AuthResponseVm createUserProfile(UserProfileRequestDTO requestDto) {
		
		 if (accountRepository.existsByEmail(requestDto.getAccountDTO().getEmail())) {
		        throw new RuntimeException("email.already.in.use");
		    }
 
	    UserAccount account = new UserAccount();
	    account.setEmail(requestDto.getAccountDTO().getEmail());
	    account.setPassword(passwordEncoder.encode( requestDto.getAccountDTO().getPassword() ) );

	    // 👈 Default Role
	    Role defaultRole = roleRepository.findByName(RoleEnum.USER)
	            .orElseThrow(() -> new RuntimeException("default.role.not.found"));
	    account.getRoles().add(defaultRole);
	    
	    
	    UserProfile profile = new UserProfile();
	    profile = profileMapper.toEntity(requestDto);
		 
// 			use alone make account.getProfile() = null	 
		 profile.setUserAccount(account);  
		 
//			 to link inverse side to owner side account.getProfile() = value use alone not save account in DB
		 account.setProfile(profile);

	    UserProfile savedProfile = profileRepository.save(profile);

//	    System.out.print("----------->" + account.getProfile() );
	    
	 // 5️⃣ generate JWT using JwtService
	    String token = jwtService.generateToken(
	            new org.springframework.security.core.userdetails.User(
	                    account.getEmail(),
	                    account.getPassword(),
	                    account.getRoles()
	                           .stream()
	                           .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getName()))
	                           .toList()
	            )
	    );

	    // 6️⃣ return AuthResponseVm
	    return new AuthResponseVm(token);
	}


	@Override
	public AuthResponseVm loginToProfile(UserAccountRequestDTO reqAccountDto) {

	    // 1️⃣ Fetch the user account from the database using the email
	    //    If the email does not exist, throw an exception
	    UserAccount account = accountRepository
	            .findByEmail(reqAccountDto.getEmail())
	            .orElseThrow(() -> new RuntimeException("Invalid email or password"));

	    // 2️⃣ Verify the password
	    //    Compare the raw password from the request with the encoded password stored in DB
	    if (!passwordEncoder.matches(reqAccountDto.getPassword(), account.getPassword())) {
	        throw new RuntimeException("Invalid email or password");
	    }

	    // 3️⃣ Generate JWT token
	    //    Convert the account roles to SimpleGrantedAuthority for Spring Security
	    //    Then build the token using JwtService
	    String token = jwtService.generateToken(
	            new org.springframework.security.core.userdetails.User(
	                    account.getEmail(), // username (email)
	                    account.getPassword(), // password (encoded)
	                    account.getRoles()
	                           .stream()
	                           .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getName()))
	                           .collect(Collectors.toList()) // list of authorities
	            )
	    );

	    // 4️⃣ Return AuthResponseVm containing the JWT token
	    return new AuthResponseVm(token);
	}

}
