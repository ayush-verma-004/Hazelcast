package com.javanic.hazlecast.repository;

import com.javanic.hazlecast.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
