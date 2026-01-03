package com.example.demo.dto.user_account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountRequestDTO {

	@NotBlank(message = "user.email.required")
	@Email(message = "user.email.invalid")
	private String email;
	
	@NotBlank(message = "user.password.required")
    private String password;
}
