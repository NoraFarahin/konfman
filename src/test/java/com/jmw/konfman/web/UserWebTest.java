package com.jmw.konfman.web;

import java.util.ResourceBundle;

import junit.framework.TestCase;
import net.sourceforge.jwebunit.junit.WebTester;

public class UserWebTest extends TestCase {
    private ResourceBundle messages;
    private WebTester wt = new WebTester();
    
    public UserWebTest(String name) {
        super(name);
        wt.getTestContext().setBaseUrl("http://localhost:25888");
        //getTestContext().setBaseUrl("http://localhost:8080");
        wt.getTestContext().setResourceBundleName("messages");
        messages = ResourceBundle.getBundle("messages");
        //getTestContext().setLocale(Locale.GERMAN);
        //getTestContext().getWebClient().setHeaderField("Accept-Language", "de");
    }

    public void setUp() {
    	//login before performing any task
    	wt.beginAt("/");
    	wt.assertTitleEquals("Welcome");
    	wt.setTextField("j_username", "admin");
    	wt.setTextField("j_password", "admin");
    	wt.clickButton("login");
    	wt.assertTitleEquals("Welcome | Konfman");
    	wt.assertLinkPresentWithExactText("Administration");
    }

    public void testAddUser() {
    	wt.gotoPage("/appadmin/userlist.html");
    	wt.assertTablePresent("userList");
    	
    	
    	int tableRowCount = wt.getTable("userList").getRowCount();
    	
    	wt.gotoPage("/appadmin/userform.html");
        assertTitleKeyMatches("userForm.title");
    	//make sure we cannot delete an item which does no yet exist
    	wt.assertButtonNotPresentWithText("Delete");

    	wt.setTextField("firstName", "FN");
        wt.setTextField("lastName", "LN");
        wt.setTextField("username", "userN");
        wt.setTextField("password", "pass");
        wt.setTextField("verifyPassword", "pass");
        wt.setTextField("phone", "1111");
        wt.setTextField("email", "hello@merc.com");
        
        wt.assertTextFieldEquals("email", "hello@merc.com");
        wt.clickButtonWithText("Save");
        assertTitleKeyMatches("userList.title");
        wt.assertTablePresent("userList");
    	wt.assertTextPresent("User LN, FN has been saved successfully.");
        wt.assertTableRowCountEquals("userList", tableRowCount+1);
        
    }

    public void testAttemptInvalidUser() {
    	wt.gotoPage("/appadmin/userlist.html");
    	wt.assertTablePresent("userList");
    	
    	
    	int tableRowCount = wt.getTable("userList").getRowCount();
    	
    	wt.gotoPage("/appadmin/userform.html");
        assertTitleKeyMatches("userForm.title");

        wt.setTextField("firstName", "FN");
        
        try{
        	wt.clickButtonWithText("Save");
        	fail("Should error on no other fields");
        }catch (Exception e){}
        
        wt.setTextField("lastName", "LN");

        try{
        	wt.clickButtonWithText("Save");
        	fail("Should error on no other fields");
        }catch (Exception e){}

        wt.setTextField("username", "userN");

        try{
        	wt.clickButtonWithText("Save");
        	fail("Should error on no other fields");
        }catch (Exception e){}
        wt.setTextField("password", "pass");

        try{
        	wt.clickButtonWithText("Save");
        	fail("Should error on no other fields");
        }catch (Exception e){}

        wt.setTextField("verifyPassword", "pass");

        try{
        	wt.clickButtonWithText("Save");
        	fail("Should error on no other fields");
        }catch (Exception e){}
        wt.setTextField("phone", "1111");

        try{
        	wt.clickButtonWithText("Save");
        	fail("Should error on no other fields");
        }catch (Exception e){}
        wt.setTextField("email", "hello@merc.com");
        
        wt.setTextField("verifyPassword", "xx");
        
        try{
        	wt.clickButtonWithText("Save");
        	fail("Should error when passwords don't match");
        }catch (Exception e){}
        
        
        wt.setTextField("fn", "");
        try{
        	wt.clickButtonWithText("Save");
        	fail("Should error on no other fields");
        }catch (Exception e){}
        
        wt.clickButtonWithText("Save");

        assertTitleKeyMatches("userList.title");
        wt.assertTablePresent("userList");
    	wt.assertTextPresent("User LN, FN has been saved successfully.");
        wt.assertTableRowCountEquals("userList", tableRowCount+1);
        
    }
/*
    public void testListUsers() {
        beginAt("/users.html");

        // check that table is present
        assertTablePresent("userList");

        //check that a set of strings are present somewhere in table
        assertTextInTable("userList",
                new String[]{"Spring", "User"});
    }

    public void testEditUser() {
        beginAt("/userform.html?id=" + getInsertedUserId());
        assertFormElementEquals("firstName", "Spring");
        submit("save");
        assertTitleKeyMatches("userList.title");
    }

    public void testDeleteUser() {
        beginAt("/userform.html?id=" + getInsertedUserId());
        assertTitleKeyMatches("userForm.title");
        submit("delete");
        assertTitleKeyMatches("userList.title");*/
    

    /**
     * Convenience method to get the id of the inserted user
     * Assumes last inserted user is "Spring User"
     * @return last id in the table
     */
    protected String getInsertedUserId() {
    	
        wt.beginAt("/users.html");
        wt.assertTablePresent("userList");
        wt.assertTextInTable("userList", "Spring");
        //String[][] tableCellValues = getTable("userList");
        return ""; //tableCellValues[tableCellValues.length-1][0];
    }

    protected void assertTitleKeyMatches(String title) {
    	wt.assertTitleEquals(messages.getString(title) + " | " + messages.getString("webapp.name")); 
    }
}
