package com.jmw.konfman.service;

import java.util.List;

import org.springframework.security.userdetails.UserDetailsService;

import com.jmw.konfman.model.User;

public interface UserManager extends UserDetailsService{
    public List getUsers();
    public User getUser(String userId);
    public void saveUser(User user);
    public void removeUser(String userId);
}
