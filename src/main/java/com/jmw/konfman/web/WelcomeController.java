package com.jmw.konfman.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jmw.konfman.model.User;
import com.jmw.konfman.service.ReservationManager;

@Controller
public class WelcomeController {
    @Autowired
    ReservationManager reservationManager;

    @RequestMapping("/welcome.*")
    public String execute(HttpServletRequest request, ModelMap model) {
    	SecurityContext ssc = (SecurityContext) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
    	Authentication auth = ssc.getAuthentication();
    	User user = (User) auth.getPrincipal();
        model.addAttribute("me", user);
        model.addAttribute("reservations", reservationManager.getCurrentUserReservations(user));
        return "welcome";
    }
}
