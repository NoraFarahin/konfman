package com.jmw.konfman.service;

import com.jmw.konfman.model.Authority;

import java.util.List;

public interface AuthorityManager {
    public List getAuthorities();
    public Authority getAuthority(String authorityId);
    public void saveAuthority(Authority authority);
    public void removeAuthority(String authorityId);
}
