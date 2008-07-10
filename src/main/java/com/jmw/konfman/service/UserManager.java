package com.jmw.konfman.service;

import com.jmw.konfman.model.User;

import java.util.List;

public interface UserManager {
    public List getUsers();
    public User getUser(String userId);
    public void saveUser(User user);
    public void removeUser(String userId);
}
