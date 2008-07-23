package com.jmw.konfman.web;

import java.util.HashMap;
import java.util.Map;

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
import org.springframework.web.servlet.mvc.AbstractWizardFormController;

import com.jmw.konfman.model.Floor;
import com.jmw.konfman.model.Room;
import com.jmw.konfman.model.User;
import com.jmw.konfman.service.RoomManager;
import com.jmw.konfman.service.UserManager;

@Controller
@RequestMapping("/**/roomform.*")
public class RoomFormController extends AbstractWizardFormController {
    private final Log log = LogFactory.getLog(RoomFormController.class);
    @Autowired
    RoomManager roomManager;
    
    @Autowired
    UserManager userManager;

    @Autowired(required = false)
	@Qualifier("beanValidator")
	Validator validator;

    public RoomFormController() {
        setCommandName("room");
        setCommandClass(Room.class);
        setPages(new String[] {"roomForm", "appadmin/usersSelect"} );
        //setSuccessView("redirect:rooms.html");
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

    /*
    public ModelAndView onSubmit(HttpServletRequest request,
                                 HttpServletResponse response, Object command,
                                 BindException errors)
            throws Exception {
        log.debug("entering 'onSubmit' method...");

        Room room = (Room) command;
        setSuccessView("redirect:rooms.html?floorId=" + room.getFloor().getId());

        if (request.getParameter("delete") != null) {
            roomManager.removeRoom(room.getId().toString());
            request.getSession().setAttribute("message", 
                    getText("room.deleted", room.getName()));
        } else {
            roomManager.saveRoom(room);
            request.getSession().setAttribute("message",
                    getText("room.saved", room.getName()));
        }

        return new ModelAndView(getSuccessView());
    }*/

    protected Object formBackingObject(HttpServletRequest request)
            throws ServletException {
        String roomId = request.getParameter("id");
        String floorId = request.getParameter("floorId");
        //System.out.println("Building Id: " + buildingId);
        String userId = request.getParameter("userId");
        String removeUserId = request.getParameter("removeUserId");
        Room room = null;
        
        if ((roomId != null) && !roomId.equals("")) {
            return roomManager.getRoom(roomId);
        } else if ((userId != null) && !userId.equals("")) {
    		try {
    			room = (Room)getCommand(request);
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
            User user = userManager.getUser(userId);
    		logger.debug("Adding user: " + user.getFullName() + " as an admin for " + room.getName());
            //for some reason or other we have to add the user to the room and the room to the user 
    		//in order for the data to be saved. Interestingly, this is not necessary on the user side
    		//of this functionality!
    		room.addAdministrator(user);
            user.addAdministeredRoom(room);
            userManager.saveUser(user);
        } else if ((removeUserId != null) && !removeUserId.equals("")) {
    		try {
    			room = (Room)getCommand(request);
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
            User user = userManager.getUser(removeUserId);
            user.removeAdminsteredRoom(room);
            room.removeAdministrator(user);
            userManager.saveUser(user);
    		logger.debug("Removing user: " + user.getFullName() + " from admin on room " + room.getName());
        }
        else {
            room = new Room();
            Floor f = new Floor();
            f.setId(Long.valueOf(floorId));
            room.setFloor(f);
        }
        return room;
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

    protected Map referenceData(HttpServletRequest request, int page){
    	if (page == 1){
    		logger.debug("providing user data");
    		Map map = new HashMap();
    		map.put("userList", userManager.getUsers());
    		map.put("submitform", "roomform");
    		map.put("submittarget", "");
    		return map;
    	}
    	return null;
    }

    @Override
	protected ModelAndView processFinish(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException error)
			throws Exception {
        Room room = (Room) command;
        if (request.getParameter("_finish").equals("Delete")) {
            roomManager.removeRoom(room.getId().toString());
            request.getSession().setAttribute("message", 
                    getText("room.deleted", room.getName()));
        } else {
            roomManager.saveRoom(room);
            //logger.debug("Saved room: " + room + " has " + room.getAdministrators().size() + " admins.");
            request.getSession().setAttribute("message",
                    getText("room.saved", room.getName()));
        }

        return new ModelAndView("redirect:./rooms.html?floorId=" + room.getFloor().getId());
	}

	protected ModelAndView processCancel(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException error)
			throws Exception {
		Room room = (Room) command;
        return new ModelAndView("redirect:./rooms.html?floorId=" + room.getFloor().getId());
	}
}
