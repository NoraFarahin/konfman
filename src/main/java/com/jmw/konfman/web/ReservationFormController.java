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

import com.jmw.konfman.model.Reservation;
import com.jmw.konfman.model.Room;
import com.jmw.konfman.model.User;
import com.jmw.konfman.service.BuildingManager;
import com.jmw.konfman.service.ReservationManager;
import com.jmw.konfman.service.RoomManager;
import com.jmw.konfman.service.UserManager;
import com.jmw.konfman.validator.ReservationValidator;

@Controller
@RequestMapping("/**/reservationform.*")
public class ReservationFormController extends AbstractWizardFormController  {
    private final Log log = LogFactory.getLog(ReservationFormController.class);
    @Autowired
    ReservationManager reservationManager;
    @Autowired
    UserManager userManager;
    @Autowired
    RoomManager roomManager;
    @Autowired
    BuildingManager buildingManager;
    
    @Autowired(required = false)
	@Qualifier("reservationValidator")
	Validator validator;
    
    public ReservationFormController() {
        setCommandName("reservation");
        setCommandClass(Reservation.class);
        setPages(new String [] {"reservationForm", "usersSelect", "roomSelect"});
        if (validator != null)
           setValidator(validator);
    }

    
    /*
    public ModelAndView processFormSubmission(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        System.out.println("processing");

        //return super.processFormSubmission(request, response, command, errors);
    }*/

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
    
    
    protected boolean suppressValidation(HttpServletRequest request, Object command) {
    	//don't validate unless we are finishing 
    	String finish = request.getParameter("_finish");
    	 if (finish == null || finish.equals("") || finish.equals("Delete")){
    		 return true;
    	 }
    	 return super.suppressValidation(request, command);
    }

    protected void validatePage(Object command, Errors errors, int page){
    	
    	validator.validate(command, errors);

    	Reservation reservation = (Reservation)command;
    	boolean conflict = reservationManager.isConflict(reservation);
    	if (conflict){
    		errors.reject("reservation.conflicted", new String[] {reservation.getComment()}, "" );
    	}
    }

    private Reservation createNewReservation(HttpServletRequest request){
    	Reservation reservation = new Reservation();
		String dest = request.getParameter("dest");
		if (dest!=null && dest.startsWith("my")){
			SecurityContext sc = (SecurityContext)request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
			Authentication auth = sc.getAuthentication();
			User user = (User)auth.getPrincipal();
			reservation.setUser(user);
		}
    	return reservation;
    }
    
    private Reservation getReservation(String reservationId){
    	return reservationManager.getReservation(reservationId);
    }
    
    private Reservation updateUser(Reservation reservation, String userId){
        User user = userManager.getUser(userId);
		log.debug("Changed reservation's user: "  + user.getFullName());
        reservation.setUser(user);
        return reservation;
    }
    
    private Reservation updateRoom(Reservation reservation, String roomId){
        Room room = roomManager.getRoom(roomId);
        reservation.setRoom(room);
		log.debug("Changed reservation's room to: "  + room.getName());
        return reservation;
    }
    
    
    private void setDestination(HttpServletRequest request, Reservation reservation){
    	//figure out how we got here and save it so that we can get back there later
    	String dest = request.getParameter("dest");
    	if (dest != null && !dest.equals("")){
    		if (dest.startsWith("user")){
    			dest = dest + "?userId="+ reservation.getUser().getId();
    		} else if (dest.startsWith("reservation")){
    			dest = dest + "?roomId=" + reservation.getRoom().getId();
    		}
	        request.getSession().setAttribute("destination", dest);
    	}
    }
    
    protected int getTargetPage(HttpServletRequest request, int currentPage){
    	this.setAllowDirtyBack(true);
    	this.setAllowDirtyForward(true);
    	return super.getTargetPage(request, currentPage);
    }
    
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        Reservation reservation = null;
		try {
			reservation = (Reservation)this.getCommand(request);
		} catch (Exception e) {
			logger.debug("Creating a new reservation");
			reservation = createNewReservation(request);
		}
        String reservationId = request.getParameter("id");
        String roomId = request.getParameter("roomId");
        String userId = request.getParameter("userId");
        
        //if we are requesting an existing reservation 
        if ((reservationId != null) && !reservationId.equals("")) {
            reservation = getReservation(reservationId);
        }   
        if ((userId != null) && !userId.equals("")) {
        	reservation = this.updateUser(reservation, userId);
        }
        if ((roomId != null) && !roomId.equals("")) {
        	reservation = this.updateRoom(reservation, roomId);
        }
        setDestination(request, reservation);
        return reservation;
    }
    
    @SuppressWarnings("unchecked")
	protected Map referenceData(HttpServletRequest request, int page){
    	if (page == 1){
    		logger.debug("providing user data");
    		Map map = new HashMap();
    		map.put("userList", userManager.getUsers());
    		map.put("submitform", "reservationform");
    		return map;
    	} else if (page > 1 ){
    		logger.debug("providing building data");
    		Map map = new HashMap();
    		map.put("buildingList", buildingManager.getBuildings());
    		map.put("submitform", "reservationform");
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
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		Reservation reservation = (Reservation)command;
		//String roomId = reservation.getRoom().getId().toString();
		if (request.getParameter("_finish").equals("Delete")){
			logger.debug("Removing reservation: " + reservation.getComment() + "/" + reservation.getId());
			reservationManager.removeReservation(reservation.getId().toString());		
	        request.getSession().setAttribute("message", 
	                getText("reservation.deleted", reservation.getComment()));
		} else {
			boolean b = reservationManager.saveReservation(reservation);
			if (b == true){
				logger.debug("Reservation saved/updated");
				request.getSession().setAttribute("message",
                    getText("reservation.saved", reservation.getComment()));
			} else {
		        request.getSession().setAttribute("message", 
		                getText("reservation.conflicted", reservation.getComment()));
			}
			
		}
		System.out.println("Destination: " + request.getSession().getAttribute("destination"));
		return new ModelAndView("redirect:" + request.getSession().getAttribute("destination"));
	}

	protected ModelAndView processCancel(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		//Reservation reservation = (Reservation)command;
		logger.debug("Canceling reservation change...");
		return new ModelAndView("redirect:" + request.getSession().getAttribute("destination"));
	}
}
