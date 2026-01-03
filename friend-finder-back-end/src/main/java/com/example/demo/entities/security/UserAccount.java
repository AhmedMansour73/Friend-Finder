package com.example.demo.entities.security;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.example.demo.entities.UserProfile;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ff_user_accounts")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToOne(mappedBy = "userAccount")
    private UserProfile profile;
    
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "ff_user_roles",
        joinColumns = @JoinColumn(name = "user_account_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

}
