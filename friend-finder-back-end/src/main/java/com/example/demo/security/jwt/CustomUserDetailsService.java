package com.example.demo.security.jwt;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import com.example.demo.entities.security.UserAccount;
import com.example.demo.repository.UserAccountRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserAccountRepository userRepo;

    public CustomUserDetailsService(UserAccountRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) 
            throws UsernameNotFoundException {
    	
        // get from DB 
        UserAccount user = userRepo.findByEmail(username)
                .orElseThrow(() ->
                    new UsernameNotFoundException("User not found: " + username)
                );

        // convert to UserDetails to use in JWT Filter
        return new User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles()  // Set<Role>
                    .stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                    .collect(Collectors.toList()) // List<GrantedAuthority>
        );
    }
}
