package com.ond.ond.repositories;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ond.ond.models.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> getUsuarioById(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndPassword(String email, String password);

}
