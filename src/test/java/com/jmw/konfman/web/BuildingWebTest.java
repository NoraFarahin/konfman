package com.jmw.konfman.web;

import java.util.ResourceBundle;

import junit.framework.TestCase;
import net.sourceforge.jwebunit.html.Cell;
import net.sourceforge.jwebunit.html.Row;
import net.sourceforge.jwebunit.html.Table;
import net.sourceforge.jwebunit.junit.WebTester;

public class BuildingWebTest extends TestCase {
    private ResourceBundle messages;
    private WebTester wt = new WebTester();
    
    private String lastInserted = "";

    public BuildingWebTest(String name) {
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

    public void testAddBuilding() {
    	wt.gotoPage("/appadmin/buildings.html");
    	wt.assertTablePresent("buildingList");
    	
    	//make sure we cannot delete an item which does no yet exist
    	wt.assertButtonNotPresentWithText("Delete");
    	
    	int tableRowCount = wt.getTable("buildingList").getRowCount();
    	
    	wt.gotoPage("/appadmin/buildingform.html");
        assertTitleKeyMatches("buildingForm.title");
        wt.setTextField("name", "BuildingTest");
        wt.setTextField("title", "BTitle");
        wt.submit("save");
        assertTitleKeyMatches("buildingList.title");
        wt.assertTablePresent("buildingList");
    	wt.assertTextPresent("Building BuildingTest has been saved successfully.");
        wt.assertTableRowCountEquals("buildingList", tableRowCount+1);
        
        this.getInsertedBuildingId();
    }

    public void testListBuildings() {
    	wt.gotoPage("/appadmin/buildings.html");

    	wt.assertTablePresent("buildingList");

    	int tableRowCount = wt.getTable("buildingList").getRowCount();
    	assertTrue(tableRowCount > 2);
    	wt.assertTextInTable("buildingList", "BuildingTest");
    }

    public void testEditBuildingCancel() {
    	wt.gotoPage("/appadmin/buildings.html");
    	int tableRowCount = wt.getTable("buildingList").getRowCount();
    	wt.gotoPage("/appadmin/buildingform.html?id=" + getInsertedBuildingId());
    	wt.assertFormPresent();
    	wt.assertFormElementPresent("name");
    	wt.assertTextFieldEquals("name", "BuildingTest");
    	wt.setTextField("name", "BuildingTitle2");
    	wt.submit("cancel");
    	assertTitleKeyMatches("buildingList.title");
    	wt.assertTableRowCountEquals("buildingList", tableRowCount);
    	wt.assertTextInTable("buildingList", "BuildingTest");
    }

    public void testEditBuildingSave() {
    	wt.gotoPage("/appadmin/buildingform.html?id=" + getInsertedBuildingId());
    	wt.assertFormPresent();
    	wt.assertFormElementPresent("name");
    	wt.assertTextFieldEquals("name", "BuildingTest");
    	wt.setTextField("name", "BuildingTitle1");
    	wt.submit("save");
    	assertTitleKeyMatches("buildingList.title");
    	wt.assertTextPresent("Building BuildingTitle1 has been saved successfully.");
    	wt.assertTextInTable("buildingList", "BuildingTitle1");
    }

    public void testDeleteBuilding() {
        System.out.println("\n\n\n\n\n" + lastInserted + "\n\n\n\n");

        wt.gotoPage("/appadmin/buildings.html");
    	int tableRowCount = wt.getTable("buildingList").getRowCount();
    	wt.gotoPage("/appadmin/buildingform.html?id=" + getInsertedBuildingId());
    	assertTitleKeyMatches("buildingForm.title");
    	wt.submit("delete");
    	assertTitleKeyMatches("buildingList.title");
    	wt.assertTablePresent("buildingList");
    	wt.assertTableRowCountEquals("buildingList", tableRowCount-1);
    }

    /**
     * Convenience method to get the id of the inserted building
     * @return last id in the table
     */
    protected String getInsertedBuildingId() {
    	wt.gotoPage("/buildings.html");
    	wt.assertTablePresent("buildingList");
    	int index = wt.getPageSource().lastIndexOf("buildingform.html?id=") + 21;
    	lastInserted = wt.getPageSource().substring(index, index+3);

    	System.out.println("\n\n\n\n\n" + lastInserted + "\n\n\n\n" + index);

    	return lastInserted;
    	
    }
    
    protected void assertTitleKeyMatches(String title) {
    	wt.assertTitleEquals(messages.getString(title) + " | " + messages.getString("webapp.name")); 
    }
}
