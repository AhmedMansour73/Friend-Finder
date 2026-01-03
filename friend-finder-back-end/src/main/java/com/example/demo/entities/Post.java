package com.example.demo.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ff_posts")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Post {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;          // content
    private String imageUrl;         // image
    private String videoUrl;         // video

    @ManyToOne
    @JoinColumn(name = "user_profile_id")
    private UserProfile userProfile;

//    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
//    private List<Comment> comments = new ArrayList<>();
//
//    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
//    private List<Like> likes = new ArrayList<>();

    private LocalDateTime createdAt = LocalDateTime.now();

}
