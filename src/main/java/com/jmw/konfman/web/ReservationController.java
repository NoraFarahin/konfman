package com.jmw.konfman.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jmw.konfman.service.RoomManager;
import com.jmw.konfman.service.UserManager;

@Controller
public class ReservationController {
    @Autowired
    RoomManager roomManager;

    @Autowired
    UserManager userManager;

    @RequestMapping("/**/reservations.*")
    public String execute(ModelMap model, @RequestParam(value="roomId") String roomId) {
        model.addAttribute("room", roomManager.getRoom(roomId));
        return "reservationList";
    }

    @RequestMapping("/appadmin/userreservations.*")
    public String userReservations(ModelMap model, @RequestParam(value="userId") String userId) {
        model.addAttribute("user", userManager.getUser(userId));
        return "appadmin/userReservations";
    }

    @RequestMapping("/myreservations.*")
    public String myReservations(ModelMap model) {
    	model.addAttribute("me", userManager.getUser("1"));
        return "myReservations";
    }
}
