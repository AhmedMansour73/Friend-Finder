package com.example.demo.security;

import java.util.Arrays;

//import com.example.demo.config.jwt.JwtFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.demo.security.jwt.CustomUserDetailsService;
import com.example.demo.security.jwt.JwtFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	private final JwtFilter jwtFilter;
	private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(JwtFilter jwtFilter,CustomUserDetailsService userDetailsService) {
    	
        this.jwtFilter = jwtFilter;
        this.userDetailsService = userDetailsService;
    }

	// Security Filter chain
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // ❌ CSRF not with JWT
            .csrf(csrf -> csrf.disable())

            // 🧠 JWT = Stateless
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // 🔓 Endpoints 
            .authorizeHttpRequests(auth -> auth
                .requestMatchers( "/auth/**" ).permitAll()
//                .requestMatchers("/api/users/login").permitAll()
                .anyRequest().authenticated()
                
            );

        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        http.httpBasic(httpBasic -> httpBasic.disable()); // when use Jwt close this
        http.formLogin(form -> form.disable());
        
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
	
	 @Bean
	    public CorsConfigurationSource corsConfigurationSource() {
	        CorsConfiguration configuration = new CorsConfiguration();
	        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
	        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
	        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", configuration);
	        return source;
	    }
	
	 @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	 
	 /*
	  * Verifies login credentials (username/password)
	  * Retrieves data (username + password)
	  * Verifies against UserDetailsService
	  * Uses PasswordEncoder to compare passwords
	  */
	 @Bean
	 public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
	     AuthenticationManagerBuilder authBuilder = 
	         http.getSharedObject(AuthenticationManagerBuilder.class);

	     authBuilder
	         .userDetailsService(userDetailsService)
	         .passwordEncoder(passwordEncoder());

	     return authBuilder.build();
	 }
}
