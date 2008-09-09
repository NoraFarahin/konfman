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

    public void testGetUsers() {
    	int userCount = userManager.getUsers().size();
        User kid1 = new User();
        kid1.setFirstName("Abbie");
        kid1.setLastName("Raible");
        User kid2 = new User();
        kid2.setFirstName("Jack");
        kid2.setLastName("Raible");
        userManager.saveUser(kid1);
        userManager.saveUser(kid2);

        assertEquals(userCount + 2, userManager.getUsers().size());
        
        userManager.removeUser(kid1.getId().toString());
        userManager.removeUser(kid2.getId().toString());
        
        assertEquals(userCount, userManager.getUsers().size());

        try{
        	userManager.removeUser(kid1.getId().toString());
        	fail("we should not be able to remove an object which has already been removed.");
        }catch (Exception e){
        	
        }
        
    }
}
