package com.example.demo.dto.user_profile;

import com.example.demo.dto.user_account.UserAccountRequestDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileRequestDTO {
	
	@NotBlank(message = "user.fname.required")
	private String firstName;
	@NotBlank(message = "user.lname.required")
    private String lastName;
	
    private String biography;
    private String profilePicture;
    
    @Valid
    private UserAccountRequestDTO accountDTO;


}
