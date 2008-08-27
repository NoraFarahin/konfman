package com.jmw.konfman.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmw.konfman.dao.AuthorityDao;
import com.jmw.konfman.model.Authority;
import com.jmw.konfman.service.AuthorityManager;

@Service(value = "authorityManager")
public class AuthorityManagerImpl implements AuthorityManager {
    @Autowired
    AuthorityDao dao;

    public void setAuthorityDao(AuthorityDao dao) {
        this.dao = dao;
    }

    public List getAuthorities() {
        return dao.getAuthorities();
    }

    public Authority getAuthority(String userId) {
        return dao.getAuthority(Long.valueOf(userId));
    }

    public void saveAuthority(Authority user) {
        dao.saveAuthority(user);
    }

    public void removeAuthority(String userId) {
        dao.removeAuthority(Long.valueOf(userId));
    }
    
}
