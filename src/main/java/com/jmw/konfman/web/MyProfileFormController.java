package com.jmw.konfman.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractWizardFormController;

import com.jmw.konfman.model.Floor;
import com.jmw.konfman.model.User;
import com.jmw.konfman.service.BuildingManager;
import com.jmw.konfman.service.FloorManager;
import com.jmw.konfman.service.UserManager;

@Controller
@RequestMapping("/myprofile.*")
public class MyProfileFormController extends AbstractWizardFormController {
    private final Log log = LogFactory.getLog(MyProfileFormController.class);
    @Autowired
    UserManager userManager;
    
    @Autowired
    BuildingManager buildingManager;

    @Autowired
    FloorManager floorManager;

    @Autowired
	@Qualifier("beanValidator")
	Validator validator;

    public MyProfileFormController() {
        setCommandName("user");
        setCommandClass(User.class);
        this.setPages(new String[] {"myProfileForm", "floorSelect"});
        if (validator != null)
            setValidator(validator);
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

    protected void validatePage(Object command, Errors errors, int page){
    	log.debug("Attempting to validate the submitted user.");
    	validator.validate(command, errors);
    }

    protected Object formBackingObject(HttpServletRequest request)
            throws ServletException {
    	User user = null;
		try {
			user = (User)this.getCommand(request);
		} catch (Exception e) {
			//e.printStackTrace();
		}
        
        if (user == null) {
        	SecurityContext ssc = (SecurityContext) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        	Authentication auth = ssc.getAuthentication();
        	user = (User) auth.getPrincipal();
        }
        String floorId = request.getParameter("floorId");
        if ((floorId != null) && !floorId.equals("")) {
            Floor floor = floorManager.getFloor(floorId);
            user.setDefaultFloor(floor);
        }
        
        return user;
    }
    
    protected Map referenceData(HttpServletRequest request, int page){
    	if (page >= 1){
    		logger.debug("providing building data");
    		Map map = new HashMap();
    		map.put("buildingList", buildingManager.getBuildings());
    		map.put("submitform", "myprofile.html");
    		return map;
    	}
    	return null;
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

	@Override
	protected ModelAndView processFinish(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException error)
			throws Exception {
		User user = (User)command;
        userManager.saveUser(user);
        request.getSession().setAttribute("message", 
                getText("profile.saved"));
        log.debug("Saved user: " + user.getFullName());
		return new ModelAndView("redirect:/");
	}
	
	protected ModelAndView processCancel(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		logger.debug("Canceling user change...");
		return new ModelAndView("redirect:/");
	}
}
