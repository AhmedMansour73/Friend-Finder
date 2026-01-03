package com.example.demo.entities;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.entities.security.UserAccount;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ff_user_profile")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;
    
//    description
    private String biography;

    private String profilePicture;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;

    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

//    @ManyToMany
//    @JoinTable(
//        name = "friendships",
//        joinColumns = @JoinColumn(name = "user_profile_id"),
//        inverseJoinColumns = @JoinColumn(name = "friend_id")
//    )
//    private Set<UserProfile> friends = new HashSet<>();


}
