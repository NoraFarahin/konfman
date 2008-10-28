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
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.Validator;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.jmw.konfman.model.Building;
import com.jmw.konfman.model.User;
import com.jmw.konfman.service.BuildingManager;

@Controller
@RequestMapping("/appadmin/buildingform.*")
public class BuildingFormController extends SimpleFormController {
    private final Log log = LogFactory.getLog(BuildingFormController.class);
    @Autowired
    BuildingManager buildingManager;
    
    @Autowired(required = false)
	@Qualifier("beanValidator")
	Validator validator;

    public BuildingFormController() {
        setCommandName("building");
        setCommandClass(Building.class);
        setFormView("appadmin/buildingForm");
        setSuccessView("redirect:buildings.html");
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

        return super.processFormSubmission(request, response, command, errors);
    }

    /**
     * Set up a custom property editor for converting Longs
     */
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) {
        // convert java.util.Date
        SimpleDateFormat dateFormat = new SimpleDateFormat(getText("date.format"));
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, 
                new CustomDateEditor(dateFormat, true));
        
        // convert java.lang.Long
        binder.registerCustomEditor(Long.class, null,
                new CustomNumberEditor(Long.class, null, true));
    }

    public ModelAndView onSubmit(HttpServletRequest request,
                                 HttpServletResponse response, Object command,
                                 BindException errors)
            throws Exception {
        log.debug("entering 'onSubmit' method...");

        Building building = (Building) command;
		SecurityContext sc = (SecurityContext)request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		Authentication auth = sc.getAuthentication();
        User currentUser = (User)auth.getPrincipal(); 

        if (request.getParameter("delete") != null) {
            buildingManager.removeBuilding(building.getId().toString());
			logger.info("Deleted building #" + building.getId() + " by " + currentUser.getUsername());
            request.getSession().setAttribute("message", 
                    getText("building.deleted", building.getName()));
        } else {
            buildingManager.saveBuilding(building);
			logger.info("Saved building #" + building.getId() + " by " + currentUser.getUsername());
            request.getSession().setAttribute("message",
                    getText("building.saved", building.getName()));
        }

        return new ModelAndView(getSuccessView());
    }

    protected Object formBackingObject(HttpServletRequest request)
            throws ServletException {
        String buildingId = request.getParameter("id");

        if ((buildingId != null) && !buildingId.equals("")) {
            return buildingManager.getBuilding(buildingId);
        } else {
            return new Building();
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
