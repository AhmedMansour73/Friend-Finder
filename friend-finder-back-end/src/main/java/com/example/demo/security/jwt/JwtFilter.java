package com.example.demo.security.jwt;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/*
 * The filter focuses on only 3 things:
 * 1- It reads the token from the header.
 * 2- It verifies that the token is valid.
 * 3- It places the user in the SecurityContext.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 1️⃣ Read Authorization header
        String authHeader = request.getHeader("Authorization");

        // If no token → continue filter chain
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2️⃣ Extract token
        String token = authHeader.substring(7);

        // 3️⃣ Extract username from token
        String username;
        
        try {
            username = jwtService.extractUsername(token);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 4️⃣ If user not authenticated yet
        if (username != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

            // Load user details from DB
            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(username);

            // 5️⃣ Validate token
            if (jwtService.isTokenValid(token, userDetails)) {

                // Build authentication token
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                // 6️⃣ Set authentication in context
                SecurityContextHolder.getContext()
                        .setAuthentication(authToken);
            }
        }

        // 7️⃣ Continue filter chain
        filterChain.doFilter(request, response);
    }
}
