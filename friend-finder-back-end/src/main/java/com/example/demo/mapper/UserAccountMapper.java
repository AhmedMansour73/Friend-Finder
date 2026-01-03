package com.example.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.dto.user_account.UserAccountRequestDTO;
import com.example.demo.dto.user_account.UserAccountResponseDTO;
import com.example.demo.entities.security.UserAccount;

@Mapper(componentModel = "spring")
public interface UserAccountMapper {
//  to use without Injection
//	UserAccountMapper INSTANCE = Mappers.getMapper(UserAccountMapper.class);

    @Mapping(source = "profile.firstName", target = "firstName")
    @Mapping(source = "profile.lastName", target = "lastName")
    @Mapping(source = "profile.profilePicture", target = "profilePicture")
    @Mapping(target = "roles", expression = "java(userAccount.getRoles().stream()"
    							+ ".map(r -> r.getName().name()).collect(java.util.stream.Collectors.toSet()))")
    UserAccountResponseDTO toResponseDTO(UserAccount userAccount);

    UserAccount toEntity(UserAccountRequestDTO dto);
}
