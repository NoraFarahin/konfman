package com.jmw.konfman.web;

import net.sourceforge.jwebunit.WebTestCase;

import java.util.ResourceBundle;

public class LoginWebTest extends WebTestCase {
    private ResourceBundle messages;

    public LoginWebTest(String name) {
        super(name);
        getTestContext().setBaseUrl("http://localhost:8080");
        getTestContext().setResourceBundleName("messages");
        messages = ResourceBundle.getBundle("messages");
        //getTestContext().setLocale(Locale.GERMAN);
        //getTestContext().getWebClient().setHeaderField("Accept-Language", "de");
    }

    public void testLoginPage() {
        beginAt("/");
        assertTitleEquals("Welcome");
        
        //test login
        setFormElement("j_username", "yp");
        setFormElement("j_password", "ypyp");
        submit("Login");
        this.assertTitleEquals("Welcome | Konfman");
        
    }
    
    protected void assertTitleKeyMatches(String title) {
        assertTitleEquals(messages.getString(title) + " | " + messages.getString("webapp.name")); 
    }
}
