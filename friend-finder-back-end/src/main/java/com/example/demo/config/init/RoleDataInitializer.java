package com.example.demo.config.init;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.entities.security.Role;
import com.example.demo.entities.security.RoleEnum;
import com.example.demo.repository.RoleRepository;

@Component
public class RoleDataInitializer implements CommandLineRunner{
	private final RoleRepository roleRepository;

    public RoleDataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {

    	 Set<RoleEnum> existingRoles = roleRepository.findAll().stream()
                 .map(Role::getName)
                 .collect(Collectors.toSet());

         
         for (RoleEnum roleName : RoleEnum.values()) {
             if (!existingRoles.contains(roleName)) {
                 Role role = new Role();
                 role.setName(roleName);
                 roleRepository.save(role);
                 System.out.println("in role num ");
             }
         }
         System.out.println("out role num ");
    }

}
