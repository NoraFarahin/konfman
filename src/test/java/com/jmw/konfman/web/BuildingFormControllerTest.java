package com.jmw.konfman.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import com.jmw.konfman.model.Building;
import com.jmw.konfman.service.BuildingManager;
import com.jmw.konfman.web.BuildingFormController;


public class BuildingFormControllerTest extends MockObjectTestCase {
    private final Log log = LogFactory.getLog(BuildingFormControllerTest.class);
    private BuildingFormController c = new BuildingFormController();
    private MockHttpServletRequest request = null;
    private ModelAndView mv = null;
    private Building building = new Building();
    private Mock mockManager = null;

    protected void setUp() throws Exception {
        super.setUp();
        mockManager = new Mock(BuildingManager.class);
        
        // manually set properties (dependencies) on buildingFormController
        c.buildingManager = (BuildingManager) mockManager.proxy();
        c.setFormView("buildingForm");
        
        // set context with messages avoid NPE when controller calls 
        // getMessageSourceAccessor().getMessage()
        StaticApplicationContext ctx = new StaticApplicationContext();
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("basename", "messages");
        ctx.registerSingleton("messageSource", ResourceBundleMessageSource.class, 
                              new MutablePropertyValues(properties));
        ctx.refresh();
        c.setApplicationContext(ctx);    
        
        // setup building values
        building.setId(1L);
        building.setName("Matt");
        building.setTitle("Raible");
    }
    
    public void testEdit() throws Exception {
        log.debug("testing edit...");
        
        // set expected behavior on manager
        mockManager.expects(once()).method("getBuilding")
                   .will(returnValue(new Building()));
        
        request = new MockHttpServletRequest("GET", "/buildingform.html");
        request.addParameter("id", building.getId().toString());
        mv = c.handleRequest(request, new MockHttpServletResponse());
        assertEquals("buildingForm", mv.getViewName());
        
        // verify expectations
        mockManager.verify();
    }

    public void testSave() throws Exception {
        // set expected behavior on manager
        // called by formBackingObject()
        mockManager.expects(once()).method("getBuilding")
                   .will(returnValue(building));
        
        Building savedBuilding = building;
        savedBuilding.setName("Updated Name");
        // called by onSubmit()
        mockManager.expects(once()).method("saveBuilding")
                   .with(eq(savedBuilding));
        
        request = new MockHttpServletRequest("POST", "/buildingform.html");
        request.addParameter("id", building.getId().toString());
        request.addParameter("name", building.getName());
        request.addParameter("name", "Updated Name");
        mv = c.handleRequest(request, new MockHttpServletResponse());
        Errors errors = (Errors) mv.getModel().get(BindException.MODEL_KEY_PREFIX + "building");
        assertNull(errors);
        assertNotNull(request.getSession().getAttribute("message"));
        
        // verify expectations
        mockManager.verify();
    }
    
    public void testRemove() throws Exception {
        // set expected behavior on manager
        // called by formBackingObject()
        mockManager.expects(once()).method("getBuilding")
                   .will(returnValue(building));
        // called by onSubmit()
        mockManager.expects(once()).method("removeBuilding").with(eq("1"));
        
        request = new MockHttpServletRequest("POST", "/buildingform.html");
        request.addParameter("delete", "");
        request.addParameter("id", building.getId().toString());
        mv = c.handleRequest(request, new MockHttpServletResponse());
        assertNotNull(request.getSession().getAttribute("message"));
        
        // verify expectations
        mockManager.verify();
    }   
}
