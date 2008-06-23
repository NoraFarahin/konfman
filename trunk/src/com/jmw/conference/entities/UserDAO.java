package com.jmw.conference.entities;

import java.util.*;

public class UserDAO extends AbstractDAO{
	
	public UserDAO(){
		super();
	}

    public User find(Integer id) {
        return (User)super.find(User.class, id);
    }
    
    public List findAll() {
        return super.findAll(User.class);
    }
}
