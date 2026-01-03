package com.example.demo.security.jwt;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.example.demo.security.SecurityConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.SystemException;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;


//TokenService or JwtUtil  to create Token
@Service
public class JwtService {

	  private final JwtParser jwtParser;
      private final Key signingKey;
      private final long tokenExpiration;
    
      public JwtService( @Value("${token.secret}") String secret,
    					@Value("${token.time}") Duration time) {
    	  
        this.tokenExpiration = time.toMillis();
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        
        this.jwtParser = Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build();
      }

    /**
     * Generate JWT token using UserDetails
     */
    public String generateToken(UserDetails userDetails) {

        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + tokenExpiration)
                )
                .signWith(signingKey)
                .compact();
    }

    /**
     * Validate token against UserDetails
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        return resolver.apply(claims);
    }
}
