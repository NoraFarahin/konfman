package com.jmw.konfman.web;

import net.sourceforge.jwebunit.WebTestCase;

import java.util.ResourceBundle;

public class FloorWebTest extends WebTestCase {
    private ResourceBundle messages;

    public FloorWebTest(String name) {
        super(name);
        getTestContext().setBaseUrl("http://localhost:8080");
        getTestContext().setResourceBundleName("messages");
        messages = ResourceBundle.getBundle("messages");
        //getTestContext().setLocale(Locale.GERMAN);
        //getTestContext().getWebClient().setHeaderField("Accept-Language", "de");
    }

    public void testWelcomePage() {
        beginAt("/");
        assertTitleKeyMatches("index.title");
    }

    public void testAddUser() {
        beginAt("/floorform.html");
        assertTitleKeyMatches("floorForm.title");
        setFormElement("name", "Spring");
        setFormElement("title", "User");
        submit("save");
        assertTitleKeyMatches("floorList.title");
    }

    public void testListUsers() {
        beginAt("/floors.html");

        // check that table is present
        assertTablePresent("floorList");

        //check that a set of strings are present somewhere in table
        assertTextInTable("floorList",
                new String[]{"Spring", "User"});
    }

    public void testEditUser() {
        beginAt("/floorform.html?id=" + getInsertedUserId());
        assertFormElementEquals("name", "Spring");
        submit("save");
        assertTitleKeyMatches("floorList.title");
    }

    public void testDeleteUser() {
        beginAt("/floorform.html?id=" + getInsertedUserId());
        assertTitleKeyMatches("floorForm.title");
        submit("delete");
        assertTitleKeyMatches("floorList.title");
    }

    /**
     * Convenience method to get the id of the inserted floor
     * Assumes last inserted floor is "Spring User"
     * @return last id in the table
     */
    protected String getInsertedUserId() {
        beginAt("/floors.html");
        assertTablePresent("floorList");
        assertTextInTable("floorList", "Spring");
        String[][] tableCellValues =
                getDialog().getSparseTableBySummaryOrId("floorList");
        return tableCellValues[tableCellValues.length-1][0];
    }

    protected void assertTitleKeyMatches(String title) {
        assertTitleEquals(messages.getString(title) + " | " + messages.getString("webapp.name")); 
    }
}
