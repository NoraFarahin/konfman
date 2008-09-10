package com.jmw.konfman.web;

import java.util.ResourceBundle;

import net.sourceforge.jwebunit.junit.WebTestCase;

public class BuildingWebTest extends WebTestCase {
    private ResourceBundle messages;

    public BuildingWebTest(String name) {
        super(name);
        getTestContext().setBaseUrl("http://localhost:8080");
        getTestContext().setResourceBundleName("messages");
        messages = ResourceBundle.getBundle("messages");
        //getTestContext().setLocale(Locale.GERMAN);
        //getTestContext().getWebClient().setHeaderField("Accept-Language", "de");
    }

    
    public void testWelcomePage() {
        beginAt("/");
        assertTitleEquals("Welcome");
    }

    /*public void testAddBuilding() {
        beginAt("/buildingform.html");
        assertTitleKeyMatches("buildingForm.title");
        setFormElement("name", "Spring");
        setFormElement("title", "User");
        submit("save");
        assertTitleKeyMatches("buildingList.title");
    }

    public void testListBuildings() {
        beginAt("/buildings.html");

        // check that table is present
        assertTablePresent("buildingList");

        //check that a set of strings are present somewhere in table
        assertTextInTable("buildingList",
                new String[]{"Spring", "User"});
    }

    public void testEditBuilding() {
        beginAt("/buildingform.html?id=" + getInsertedBuildingId());
        assertFormElementEquals("name", "Spring");
        submit("save");
        assertTitleKeyMatches("buildingList.title");
    }

    public void testDeleteBuilding() {
        beginAt("/buildingform.html?id=" + getInsertedBuildingId());
        assertTitleKeyMatches("buildingForm.title");
        submit("delete");
        assertTitleKeyMatches("buildingList.title");
    }

    /**
     * Convenience method to get the id of the inserted building
     * Assumes last inserted building is "Spring Building"
     * @return last id in the table
     *
    protected String getInsertedBuildingId() {
        beginAt("/buildings.html");
        assertTablePresent("buildingList");
        assertTextInTable("buildingList", "Spring");
        String[][] tableCellValues =
                getDialog().getSparseTableBySummaryOrId("buildingList");
        return tableCellValues[tableCellValues.length-1][0];
    }
*/
    protected void assertTitleKeyMatches(String title) {
        assertTitleEquals(messages.getString(title) + " | " + messages.getString("webapp.name")); 
    }
}
