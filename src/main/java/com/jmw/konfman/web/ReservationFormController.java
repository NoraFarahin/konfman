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
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractWizardFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.jmw.konfman.model.Reservation;
import com.jmw.konfman.model.Room;
import com.jmw.konfman.model.User;
import com.jmw.konfman.service.ReservationManager;
import com.jmw.konfman.service.UserManager;

@Controller
@RequestMapping("/reservationform.*")
public class ReservationFormController extends AbstractWizardFormController  {
    private final Log log = LogFactory.getLog(RoomFormController.class);
    @Autowired
    ReservationManager reservationManager;
    @Autowired
    UserManager userManager;

    
    @Autowired(required = false)
	@Qualifier("beanValidator")
	Validator validator;

    public ReservationFormController() {
        setCommandName("reservation");
        setCommandClass(Reservation.class);
        setPages(new String [] {"reservationForm", "usersSelect"});
        if (validator != null)
           setValidator(validator);
    }

    
    /*public ModelAndView processFormSubmission(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        System.out.println("processing");

        return super.processFormSubmission(request, response, command, errors);
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
    
    /*
    protected void validatePage(Object command, Errors errors, int page){
    	Reservation reservation = (Reservation)command;
    	System.out.println("validating: " + reservation.getComment());
    	//if (page == 0){
    		System.out.println("Errors: " + errors.getErrorCount());
    	//}
    }
    
   /* public ModelAndView onSubmit(HttpServletRequest request,
                                 HttpServletResponse response, Object command,
                                 BindException errors)
            throws Exception {
        log.debug("entering 'onSubmit' method...");

        Reservation reservation = (Reservation) command;
        if (reservation.getUser() == null){
            setSuccessView("redirect:usersselect.html?reservationId=" + reservation.getId());
        } else {
        	setSuccessView("redirect:reservations.html?roomId=" + reservation.getRoom().getId());
        }
        

        if (request.getParameter("selectuser") != null) {
            request.getSession().setAttribute("reservation", reservation);
        } else if (request.getParameter("delete") != null) {
            reservationManager.removeReservation(reservation.getId().toString());
            request.getSession().setAttribute("message", 
                    getText("reservation.deleted", reservation.getComment()));
        } else {
            reservationManager.saveReservation(reservation);
            request.getSession().setAttribute("message",
                    getText("reservation.saved", reservation.getComment()));
        }

        return new ModelAndView(getSuccessView());
    }*/

    protected Object formBackingObject(HttpServletRequest request)
            throws ServletException {
        String reservationId = request.getParameter("id");
        String roomId = request.getParameter("roomId");
        String userId = request.getParameter("userId");
        //System.out.println("Building Id: " + buildingId);
        if ((userId != null) && !userId.equals("")) {
            User user = userManager.getUser(userId);
            Reservation r = null;
			try {
				r = (Reservation)this.getCommand(request);
				log.debug("added user: "  + user.getFullName());
				//reservationManager.saveReservation(r);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            r.setUser(user);
            return r;
        } else if ((reservationId != null) && !reservationId.equals("")) {
            return reservationManager.getReservation(reservationId);
        } else {
            Reservation reservation = new Reservation();
            Room r = new Room();
            r.setId(Long.valueOf(roomId));
            reservation.setRoom(r);
            return reservation;
        }
    }
    
    protected Map referenceData(HttpServletRequest request, int page){
    	if (page == 1){
    		logger.debug("providing user data");
    		Map map = new HashMap();
    		map.put("userList", userManager.getUsers());
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
		String roomId = reservation.getRoom().getId().toString();
		if (request.getParameter("_finish").equals("Delete")){
			logger.debug("Removing reservation: " + reservation.getComment() + "/" + reservation.getId());
			reservationManager.removeReservation(reservation.getId().toString());		
	        request.getSession().setAttribute("message", 
	                getText("reservation.deleted", reservation.getComment()));
		} else {
	        String userId = request.getParameter("userId");
	        if ((userId != null) && !userId.equals("")) {
	            User user = userManager.getUser(userId);
	            reservation.setUser(user);
	        }
			reservationManager.saveReservation(reservation);
			logger.debug("Reservation saved/updated");
            request.getSession().setAttribute("message",
                    getText("reservation.saved", reservation.getComment()));
		}
		return new ModelAndView("redirect:reservations.html?roomId=" + roomId);
	}

	protected ModelAndView processCancel(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		Reservation reservation = (Reservation)command;
		logger.debug("Canceling reservation change...");
		return new ModelAndView("redirect:reservations.html?roomId=" + reservation.getRoom().getId());
	}
}
