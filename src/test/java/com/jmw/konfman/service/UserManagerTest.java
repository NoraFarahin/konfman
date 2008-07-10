package com.jmw.konfman.service;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

import com.jmw.konfman.model.User;
import com.jmw.konfman.service.UserManager;

public class UserManagerTest extends AbstractTransactionalDataSourceSpringContextTests {
    private UserManager userManager;

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    protected String[] getConfigLocations() {
        setAutowireMode(AUTOWIRE_BY_NAME);
        return new String[] {"classpath*:/WEB-INF/applicationContext*.xml"};
    }

    protected void onSetUpInTransaction() throws Exception {
        deleteFromTables(new String[] {"USERS"});
    }

    public void testGetUsers() {
        User kid1 = new User();
        kid1.setFirstName("Abbie");
        kid1.setLastName("Raible");
        User kid2 = new User();
        kid2.setFirstName("Jack");
        kid2.setLastName("Raible");
        userManager.saveUser(kid1);
        userManager.saveUser(kid2);

        assertEquals(2, userManager.getUsers().size());
    }
}
