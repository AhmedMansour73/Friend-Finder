package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.security.UserAccount;


@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long>{
	
	Optional<UserAccount> findByEmail(String email);
	
	boolean existsByEmail(String email);// faster than Optional
}
