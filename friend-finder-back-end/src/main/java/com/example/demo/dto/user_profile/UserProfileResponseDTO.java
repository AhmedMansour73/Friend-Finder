package com.example.demo.dto.user_profile;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponseDTO {

	private Long id;                  
    private String firstName;
    private String lastName;
    private String biography;
    private String profilePicture;
    private List<String> friendNames;
}
