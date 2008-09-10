package com.jmw.konfman.web;

import java.util.ResourceBundle;

import net.sourceforge.jwebunit.junit.WebTestCase;

public class LoginWebTest extends WebTestCase {
    private ResourceBundle messages;

    public LoginWebTest(String name) {
        super(name);
        getTestContext().setBaseUrl("http://localhost:8080/konfman");
        getTestContext().setResourceBundleName("messages");
        messages = ResourceBundle.getBundle("messages");
        //getTestContext().setLocale(Locale.GERMAN);
        //getTestContext().getWebClient().setHeaderField("Accept-Language", "de");
    }

    public void testLoginPage() {
        beginAt("/");
        assertTitleEquals("Welcome");
        
        //test login
        setTextField("j_username", "yp");
        setTextField("j_password", "ypyp");
        clickButton("login");
        this.assertTitleEquals("Welcome | Konfman");
        
        clickLinkWithText("Log Off");
    }
    
    public void testLoginRemeberMe() {
        beginAt("/");
        assertTitleEquals("Welcome");
        
        //test login
        setTextField("j_username", "yp");
        setTextField("j_password", "ypyp");
        
        checkCheckbox("_spring_security_remember_me");
        clickButton("login");
        this.assertTitleEquals("Welcome | Konfman");
        
        clickLinkWithText("Log Off");
    }
    
}
