package com.jmw.konfman.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jmw.konfman.service.RoomManager;

@Controller
public class ReservationController {
    @Autowired
    RoomManager roomManager;

    @RequestMapping("/reservations.*")
    public String execute(ModelMap model, @RequestParam(value="roomId") String roomId) {
        model.addAttribute("room", roomManager.getRoom(roomId));
        return "reservationList";
    }
}
