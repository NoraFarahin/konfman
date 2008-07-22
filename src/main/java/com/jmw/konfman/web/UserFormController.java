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
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.Validator;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractWizardFormController;

import com.jmw.konfman.model.Floor;
import com.jmw.konfman.model.Room;
import com.jmw.konfman.model.User;
import com.jmw.konfman.service.BuildingManager;
import com.jmw.konfman.service.FloorManager;
import com.jmw.konfman.service.RoomManager;
import com.jmw.konfman.service.UserManager;

@Controller
@RequestMapping("/userform.*")
public class UserFormController extends AbstractWizardFormController {
    private final Log log = LogFactory.getLog(UserFormController.class);
    @Autowired
    UserManager userManager;
    
    @Autowired
    BuildingManager buildingManager;

    @Autowired
    FloorManager floorManager;

    @Autowired
    RoomManager roomManager;

    @Autowired(required = false)
	@Qualifier("beanValidator")
	Validator validator;

    public UserFormController() {
        setCommandName("user");
        setCommandClass(User.class);
        this.setPages(new String[] {"userForm", "floorSelect", "roomSelect"});
        if (validator != null)
            setValidator(validator);
    }

    /*
    public ModelAndView processFormSubmission(HttpServletRequest request,
                                              HttpServletResponse response,
                                              Object command,
                                              BindException errors)
            throws Exception {
        if (request.getParameter("cancel") != null) {
            return new ModelAndView(getSuccessView());
        }

        return super.processFormSubmission(request, response, command, errors);
    } */

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

    protected Object formBackingObject(HttpServletRequest request)
            throws ServletException {
        String userId = request.getParameter("id");
        String roomId = request.getParameter("roomId");
        String removeRoomId = request.getParameter("removeRoomId");
        User user = null;
        if (roomId != null && !roomId.equals("")){
        	Room room = roomManager.getRoom(roomId);
       		try {
				user = (User) this.getCommand(request);
			} catch (Exception e) {
				e.printStackTrace();
			}
			user.addAdministeredRoom(room);
        } else if (removeRoomId != null && !removeRoomId.equals("")){
        	Room room = roomManager.getRoom(removeRoomId);
       		try {
				user = (User) this.getCommand(request);
			} catch (Exception e) {
				e.printStackTrace();
			}
			user.removeAdminsteredRoom(room);
        } else if ((userId != null) && !userId.equals("")) {
            user = userManager.getUser(userId);
        } else {
            user = new User();
        }
        return user;
    }
    
    protected Map referenceData(HttpServletRequest request, int page){
    	if (page >= 1){
    		logger.debug("providing building data");
    		Map map = new HashMap();
    		map.put("buildingList", buildingManager.getBuildings());
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
        if (request.getParameter("_finish").equals("Delete")) {
            userManager.removeUser(user.getId().toString());
            request.getSession().setAttribute("message", 
                    getText("user.deleted", user.getFullName()));
            log.debug("Deleted user");
        } else {
	        String floorId = request.getParameter("floorId");
	        if ((floorId != null) && !floorId.equals("")) {
	            Floor floor = floorManager.getFloor(floorId);
	            user.setDefaultFloor(floor);
	        }
            userManager.saveUser(user);
            request.getSession().setAttribute("message",
                    getText("user.saved", user.getFullName()));
            log.debug("Saved user: " + user.getFullName());
        }
		return new ModelAndView("redirect:appadmin/userlist.html");
	}
	
	protected ModelAndView processCancel(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		logger.debug("Canceling user change...");
		return new ModelAndView("redirect:appadmin/userlist.html");
	}
}
