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
import org.springframework.web.servlet.ModelAndView;

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
    public String execute(HttpServletRequest request, ModelMap model, @RequestParam(value="roomId") String roomId, @RequestParam(value="subset", required=false) String subset) {
        Room room = roomManager.getRoom(roomId);
        String context = (String) request.getSession().getAttribute("context");
        model.addAttribute(room);
        if (context.equals("roomadmin/")){
        	model.addAttribute("return", "./roomadmin.html");
        } else {
        	model.addAttribute("return", "./rooms.html?floorId=" + room.getFloor().getId());
        }
        
    	if (subset == null){
    		logger.debug("Loading CURRENT Reservations");
    		model.addAttribute("reservations", reservationManager.getCurrentRoomReservations(room));
    	} else if (subset.equals("past")){
    		logger.debug("Loading PAST Reservations");
    		model.addAttribute("reservations", reservationManager.getPastRoomReservations(room));
    	} else if (subset.equals("all")){
    		logger.debug("Loading ALL Reservations");
    		model.addAttribute("reservations", reservationManager.getAllRoomReservations(room));    		
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
    		model.addAttribute("reservations", reservationManager.getAllUserReservations(user));    		
    	}
        return "appadmin/userReservations";
    }

    @RequestMapping("/myreservations.*")
    public String myReservations(HttpServletRequest request, ModelMap model, @RequestParam(value="subset", required=false) String subset) {
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
    		model.addAttribute("reservations", reservationManager.getAllUserReservations(user));    		
    	}
        return "myReservations";
    }
}
