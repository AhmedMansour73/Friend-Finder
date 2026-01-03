package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.security.Role;
import com.example.demo.entities.security.RoleEnum;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	 boolean existsByName(RoleEnum name);
	 Optional<Role>  findByName(RoleEnum role);
}
