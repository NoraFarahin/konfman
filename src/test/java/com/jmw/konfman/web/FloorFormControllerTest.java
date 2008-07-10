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

import com.jmw.konfman.model.Floor;
import com.jmw.konfman.service.FloorManager;
import com.jmw.konfman.web.FloorFormController;


public class FloorFormControllerTest extends MockObjectTestCase {
    private final Log log = LogFactory.getLog(FloorFormControllerTest.class);
    private FloorFormController c = new FloorFormController();
    private MockHttpServletRequest request = null;
    private ModelAndView mv = null;
    private Floor floor = new Floor();
    private Mock mockManager = null;

    protected void setUp() throws Exception {
        super.setUp();
        mockManager = new Mock(FloorManager.class);
        
        // manually set properties (dependencies) on floorFormController
        c.floorManager = (FloorManager) mockManager.proxy();
        c.setFormView("floorForm");
        
        // set context with messages avoid NPE when controller calls 
        // getMessageSourceAccessor().getMessage()
        StaticApplicationContext ctx = new StaticApplicationContext();
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("basename", "messages");
        ctx.registerSingleton("messageSource", ResourceBundleMessageSource.class, 
                              new MutablePropertyValues(properties));
        ctx.refresh();
        c.setApplicationContext(ctx);    
        
        // setup floor values
        floor.setId(1L);
        floor.setName("Matt");
        floor.setTitle("Raible");
    }
    
    public void testEdit() throws Exception {
        log.debug("testing edit...");
        
        // set expected behavior on manager
        mockManager.expects(once()).method("getFloor")
                   .will(returnValue(new Floor()));
        
        request = new MockHttpServletRequest("GET", "/floorform.html");
        request.addParameter("id", floor.getId().toString());
        mv = c.handleRequest(request, new MockHttpServletResponse());
        assertEquals("floorForm", mv.getViewName());
        
        // verify expectations
        mockManager.verify();
    }

    public void testSave() throws Exception {
        // set expected behavior on manager
        // called by formBackingObject()
        mockManager.expects(once()).method("getFloor")
                   .will(returnValue(floor));
        
        Floor savedFloor = floor;
        savedFloor.setName("Updated Name");
        // called by onSubmit()
        mockManager.expects(once()).method("saveFloor")
                   .with(eq(savedFloor));
        
        request = new MockHttpServletRequest("POST", "/floorform.html");
        request.addParameter("id", floor.getId().toString());
        request.addParameter("name", floor.getName());
        request.addParameter("name", "Updated Name");
        mv = c.handleRequest(request, new MockHttpServletResponse());
        Errors errors = (Errors) mv.getModel().get(BindException.MODEL_KEY_PREFIX + "floor");
        assertNull(errors);
        assertNotNull(request.getSession().getAttribute("message"));
        
        // verify expectations
        mockManager.verify();
    }
    
    public void testRemove() throws Exception {
        // set expected behavior on manager
        // called by formBackingObject()
        mockManager.expects(once()).method("getFloor")
                   .will(returnValue(floor));
        // called by onSubmit()
        mockManager.expects(once()).method("removeFloor").with(eq("1"));
        
        request = new MockHttpServletRequest("POST", "/floorform.html");
        request.addParameter("delete", "");
        request.addParameter("id", floor.getId().toString());
        mv = c.handleRequest(request, new MockHttpServletResponse());
        assertNotNull(request.getSession().getAttribute("message"));
        
        // verify expectations
        mockManager.verify();
    }   
}
