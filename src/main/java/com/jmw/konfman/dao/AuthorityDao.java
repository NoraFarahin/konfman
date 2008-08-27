package com.jmw.konfman.dao;

import java.util.List;

import com.jmw.konfman.model.Authority;


public interface AuthorityDao extends Dao {
    public List getAuthorities();
    public Authority getAuthority(Long authorityId);
    public void saveAuthority(Authority authority);
    public void removeAuthority(Long authorityId);
}
