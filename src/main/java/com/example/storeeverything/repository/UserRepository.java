package com.example.storeeverything.repository;

import com.example.storeeverything.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);

    Optional<User> findById(Long id);
}
