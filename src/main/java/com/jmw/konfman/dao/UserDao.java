package com.jmw.konfman.dao;

import com.jmw.konfman.model.User;

import java.util.List;


public interface UserDao extends Dao {
    public List getUsers();
    public User getUser(Long userId);
    public User getUser(String username);
    public void saveUser(User user);
    public void removeUser(Long userId);
}
