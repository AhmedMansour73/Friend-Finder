package com.example.demo.dto.user_account;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountResponseDTO {
	
	private Long id;
    private String email;
    private LocalDateTime createdAt;

    private String firstName;
    private String lastName;
    private String profilePicture;
    
    private Set<String> roles;

}
