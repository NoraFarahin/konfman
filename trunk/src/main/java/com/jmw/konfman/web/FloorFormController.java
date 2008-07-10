package com.jmw.konfman.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.Validator;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.jmw.konfman.model.Building;
import com.jmw.konfman.model.Floor;
import com.jmw.konfman.service.FloorManager;

@Controller
@RequestMapping("/floorform.*")
public class FloorFormController extends SimpleFormController {
    private final Log log = LogFactory.getLog(FloorFormController.class);
    @Autowired
    FloorManager floorManager;
    
    @Autowired(required = false)
	@Qualifier("beanValidator")
	Validator validator;

    public FloorFormController() {
        setCommandName("floor");
        setCommandClass(Floor.class);
        setFormView("floorForm");
        setSuccessView("redirect:floors.html");
        if (validator != null)
            setValidator(validator);
    }

    public ModelAndView processFormSubmission(HttpServletRequest request,
                                              HttpServletResponse response,
                                              Object command,
                                              BindException errors)
            throws Exception {
        if (request.getParameter("cancel") != null) {
            return new ModelAndView(getSuccessView());
        }
        //System.out.println("BuildingId: " + command.toString());

        return super.processFormSubmission(request, response, command, errors);
    }

    /**
     * Set up a custom property editor for converting Longs
     */
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) {
       
        // convert java.lang.Long
        binder.registerCustomEditor(Long.class, null,
                new CustomNumberEditor(Long.class, null, true));
    }

    public ModelAndView onSubmit(HttpServletRequest request,
                                 HttpServletResponse response, Object command,
                                 BindException errors)
            throws Exception {
        log.debug("entering 'onSubmit' method...");

        Floor floor = (Floor) command;
        setSuccessView("redirect:floors.html?buildingId=" + floor.getBuilding().getId());

        if (request.getParameter("delete") != null) {
            floorManager.removeFloor(floor.getId().toString());
            request.getSession().setAttribute("message", 
                    getText("floor.deleted", floor.getName()));
        } else {
            floorManager.saveFloor(floor);
            request.getSession().setAttribute("message",
                    getText("floor.saved", floor.getName()));
        }

        return new ModelAndView(getSuccessView());
    }

    protected Object formBackingObject(HttpServletRequest request)
            throws ServletException {
        String floorId = request.getParameter("id");
        String buildingId = request.getParameter("buildingId");
        //System.out.println("Building Id: " + buildingId);
        if ((floorId != null) && !floorId.equals("")) {
            return floorManager.getFloor(floorId);
        } else {
            Floor floor = new Floor();
            Building b = new Building();
            b.setId(Long.valueOf(buildingId));
            floor.setBuilding(b);
            return floor;
        }
    }
    
    /**
     * Convenience method for getting a i18n key's value.  Calling
     * getMessageSourceAccessor() is used because the RequestContext variable
     * is not set in unit tests b/c there's no DispatchServlet Request.
     *
     * @param msgKey the i18n key to lookup
     * @return the message for the key
     */
    public String getText(String msgKey) {
        return getMessageSourceAccessor().getMessage(msgKey);
    }

    /**
     * Convenient method for getting a i18n key's value with a single
     * string argument.
     *
     * @param msgKey the i18n key to lookup
     * @param arg arguments to substitute into key's value
     * @return the message for the key
     */
    public String getText(String msgKey, String arg) {
        return getText(msgKey, new Object[] { arg });
    }

    /**
     * Convenience method for getting a i18n key's value with arguments.
     *
     * @param msgKey the i18n key to lookup
     * @param args arguments to substitute into key's value
     * @return the message for the key
     */
    public String getText(String msgKey, Object[] args) {
        return getMessageSourceAccessor().getMessage(msgKey, args);
    }
}
