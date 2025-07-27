package com.javanic.hazlecast.service;

import com.javanic.hazlecast.model.User;
import com.javanic.hazlecast.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Cacheable(cacheNames = {"userCache"})
    public List<User> getAllUsers() {
        System.out.println("Fetching all users from the database");
        // This will fetch all users from the database using the UserRepository
        return userRepository.findAll();
    }

    @Cacheable(cacheNames = {"userCache"}, key = "#id", unless = "#result == null")
    public User getUser(int id){
        System.out.println("Fetching user with ID: " + id);
        // This will fetch a user by ID from the database using the UserRepository
        return userRepository.findById(id).orElse(null);
    }

    @CacheEvict(cacheNames = {"userCache"})
    public String delete(int id){
        System.out.println("Deleting user with ID: " + id);
        // This will delete a user by ID from the database using the UserRepository
        userRepository.deleteById(id);
        return "User with ID: " + id + " deleted successfully";
    }
}
