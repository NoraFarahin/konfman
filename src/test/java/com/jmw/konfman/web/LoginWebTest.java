package com.jmw.konfman.web;

import junit.framework.TestCase;
import net.sourceforge.jwebunit.junit.WebTester;

public class LoginWebTest extends TestCase {
    //private ResourceBundle messages;
	private WebTester wt = new WebTester();

    public LoginWebTest(String name) {
        super(name);
        //wt.setTestingEngineKey(TestingEngineRegistry.TESTING_ENGINE_SELENIUM);
        
        wt.getTestContext().setBaseUrl("http://localhost:25888");
        //getTestContext().setBaseUrl("http://localhost:8080/konfman");
        //getTestContext().setResourceBundleName("messages");
        //messages = ResourceBundle.getBundle("messages");
        //getTestContext().setLocale(Locale.GERMAN);
        //getTestContext().getWebClient().setHeaderField("Accept-Language", "de");
    }

    public void testLoginAndOff() {
        wt.beginAt("/");
        wt.assertTitleEquals("Welcome");
        
        //test login
        wt.setTextField("j_username", "admin");
        wt.setTextField("j_password", "admin");
        wt.clickButton("login");
        wt.assertTitleEquals("Welcome | Konfman");
        
        wt.clickLinkWithText("Log Off");
        
        wt.assertTitleEquals("Welcome");
        wt.clickLinkWithText("Home");
    	wt.assertTitleEquals("Welcome");
        
    }
    
    public void testAppAdminLogin() {
        wt.beginAt("/");
        wt.assertTitleEquals("Welcome");
        
        //test login
        wt.setTextField("j_username", "admin");
        wt.setTextField("j_password", "admin");
        wt.clickButton("login");
        wt.assertTitleEquals("Welcome | Konfman");
        wt.assertLinkPresentWithText("Administration");
        
        wt.clickLinkWithText("Log Off");
    }

    public void testRoomAdminLogin() {
        wt.beginAt("/");
        wt.assertTitleEquals("Welcome");
        
        //test login
        wt.setTextField("j_username", "room");
        wt.setTextField("j_password", "room");
        wt.clickButton("login");
        wt.assertTitleEquals("Welcome | Konfman");
        
        wt.assertLinkNotPresentWithText("Administration");
        wt.assertLinkPresentWithText("Room Admin");

        wt.clickLinkWithText("Log Off");
        
    }

    public void testUserLogin() {
        wt.beginAt("/");
        wt.assertTitleEquals("Welcome");
        
        //test login
        wt.setTextField("j_username", "user");
        wt.setTextField("j_password", "user");
        wt.clickButton("login");
        wt.assertTitleEquals("Welcome | Konfman");
        
        wt.assertLinkNotPresentWithText("Administration");
        wt.assertLinkNotPresentWithText("Room Admin");

        wt.clickLinkWithText("Log Off");
        
    }

    public void testLoginRemeberMe() {
    	wt.beginAt("/");
    	wt.assertTitleEquals("Welcome");
        
        //test login
    	wt.setTextField("j_username", "admin");
    	wt.setTextField("j_password", "admin");
        
    	wt.checkCheckbox("_spring_security_remember_me");
    	wt.clickButton("login");
    	wt.assertTitleEquals("Welcome | Konfman");
        
        
        wt.gotoPage("/login.jsp");
        wt.assertTitleEquals("Welcome");
        wt.clickLinkWithText("Home");
    	wt.assertTitleEquals("Welcome | Konfman");

        wt.clickLinkWithText("Log Off");
    }
    
    public void testFailedLogin() {
    	wt.beginAt("/");
    	wt.assertTitleEquals("Welcome");
        
        //test login
    	wt.setTextField("j_username", "x");
    	wt.setTextField("j_password", "x");
        
    	wt.checkCheckbox("_spring_security_remember_me");
    	wt.clickButton("login");
    	wt.assertTitleEquals("Welcome");
        wt.assertTextPresent("Your login attempt was not successful, try again.");
    }


}
