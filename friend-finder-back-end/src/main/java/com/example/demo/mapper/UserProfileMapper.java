package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.dto.user_profile.UserProfileRequestDTO;
import com.example.demo.dto.user_profile.UserProfileResponseDTO;
import com.example.demo.entities.UserProfile;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
//	//  to use without Injection
//	UserProfileMapper INSTANCE = Mappers.getMapper(UserProfileMapper.class);

    UserProfileResponseDTO toResponseDTO(UserProfile userProfile);

    UserProfile toEntity(UserProfileRequestDTO dto);
    
    
    List<UserProfileResponseDTO> toResponseDTOList(List<UserProfile> userProfiles);

}
