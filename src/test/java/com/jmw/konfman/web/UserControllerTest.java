package com.jmw.konfman.web;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.ui.ModelMap;

import com.jmw.konfman.model.User;
import com.jmw.konfman.service.UserManager;
import com.jmw.konfman.web.UserController;

import java.util.ArrayList;
import java.util.List;

public class UserControllerTest extends MockObjectTestCase {
    private UserController c = new UserController();
    private Mock mockManager = null;

    protected void setUp() throws Exception {
        mockManager = new Mock(UserManager.class);
        c.userManager = (UserManager) mockManager.proxy();
    }

    public void testUserList() throws Exception {
        // set expected behavior on manager
        User user1 = new User();
        user1.setFirstName("ControllerTest");
        List<User> users = new ArrayList<User>();
        users.add(user1);
        
        mockManager.expects(once()).method("getUsers")
                   .will(returnValue(users));

        ModelMap map = new ModelMap();
        String result = c.execute(map);
        assertFalse(map.isEmpty());
        assertNotNull(map.get("userList"));
        assertEquals("userList", result);
        
        // verify expectations
        mockManager.verify();
    }

    public void testUsersSelect() throws Exception {
        // set expected behavior on manager
        User user1 = new User();
        user1.setFirstName("ControllerTest");
        List<User> users = new ArrayList<User>();
        users.add(user1);
        
        mockManager.expects(once()).method("getUsers")
                   .will(returnValue(users));

        ModelMap map = new ModelMap();
        String result = c.executeSelect(map);
        assertFalse(map.isEmpty());
        assertNotNull(map.get("userList"));
        assertEquals("usersSelect", result);
        
        // verify expectations
        mockManager.verify();
    }
}
