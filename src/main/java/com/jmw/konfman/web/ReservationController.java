package com.jmw.konfman.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jmw.konfman.model.Room;
import com.jmw.konfman.model.User;
import com.jmw.konfman.service.ReservationManager;
import com.jmw.konfman.service.RoomManager;
import com.jmw.konfman.service.UserManager;

@Controller
public class ReservationController {
    Log logger = LogFactory.getLog(ReservationController.class);

    @Autowired
    RoomManager roomManager;

    @Autowired
    UserManager userManager;
    
    @Autowired
    ReservationManager reservationManager;

    @RequestMapping("/**/reservations.*")
    public String execute(ModelMap model, @RequestParam(value="roomId") String roomId, @RequestParam(value="subset", required=false) String subset) {
        Room room = roomManager.getRoom(roomId);
    	model.addAttribute("room", room);
    	if (subset == null){
    		logger.debug("Loading CURRENT Reservations");
    		model.addAttribute("reservations", reservationManager.getCurrentRoomReservations(room));
    	} else if (subset.equals("past")){
    		logger.debug("Loading PAST Reservations");
    		model.addAttribute("reservations", reservationManager.getPastRoomReservations(room));
    	} else if (subset.equals("all")){
    		logger.debug("Loading ALL Reservations");
    		model.addAttribute("reservations", room.getReservations());    		
    	}
        return "reservationList";
    }

    @RequestMapping("/appadmin/userreservations.*")
    public String userReservations(ModelMap model, @RequestParam(value="userId") String userId, @RequestParam(value="subset", required=false) String subset) {
    	User user = userManager.getUser(userId);
    	model.addAttribute("me", user);
    	if (subset == null){
    		logger.debug("Loading CURRENT Reservations");
    		model.addAttribute("reservations", reservationManager.getCurrentUserReservations(user));
    	} else if (subset.equals("past")){
    		logger.debug("Loading PAST Reservations");
    		model.addAttribute("reservations", reservationManager.getPastUserReservations(user));
    	} else if (subset.equals("all")){
    		logger.debug("Loading ALL Reservations");
    		model.addAttribute("reservations", user.getReservations());    		
    	}
        return "appadmin/userReservations";
    }

    @RequestMapping("/myreservations.*")
    public String myReservations(HttpServletRequest request, ModelMap model, @RequestParam(value="subset", required=false) String subset) {
    	//TODO make the user object or at least ID pulled from the session
    	SecurityContext ssc = (SecurityContext) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
    	Authentication auth = ssc.getAuthentication();
    	User user = (User) auth.getPrincipal();
    	model.addAttribute("me", user);
    	if (subset == null){
    		logger.debug("Loading CURRENT Reservations");
    		model.addAttribute("reservations", reservationManager.getCurrentUserReservations(user));
    	} else if (subset.equals("past")){
    		logger.debug("Loading PAST Reservations");
    		model.addAttribute("reservations", reservationManager.getPastUserReservations(user));
    	} else if (subset.equals("all")){
    		logger.debug("Loading ALL Reservations");
    		model.addAttribute("reservations", user.getReservations());    		
    	}
        return "myReservations";
    }
}
