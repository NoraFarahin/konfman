package com.jmw.konfman.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jmw.konfman.dao.UserDao;
import com.jmw.konfman.dao.hibernate.BuildingDaoHibernate;
import com.jmw.konfman.model.User;
import com.jmw.konfman.service.UserManager;

@Service(value = "userManager")
public class UserManagerImpl implements UserManager, UserDetailsService{
	 Log logger = LogFactory.getLog(UserManagerImpl.class);
    @Autowired
    UserDao dao;

    public void setUserDao(UserDao dao) {
        this.dao = dao;
    }

    public List getUsers() {
        return dao.getUsers();
    }

    public User getUser(String userId) {
        return dao.getUser(Long.valueOf(userId));
    }

    public void saveUser(User user) {
        dao.saveUser(user);
    }

    public void removeUser(String userId) {
        dao.removeUser(Long.valueOf(userId));
    }

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		logger.debug("Looking up user: " + username);
		User user = dao.getUser(username);
		logger.debug("Found user: " + user); 
		return user;
        //return dao.getUser(Long.valueOf("1"));
	}
}
