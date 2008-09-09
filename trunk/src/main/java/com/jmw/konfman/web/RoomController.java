package com.jmw.konfman.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jmw.konfman.service.FloorManager;

@Controller
public class RoomController {
    @Autowired
    FloorManager floorManager;

    @RequestMapping("/appadmin/rooms.*")
    public String execute(HttpServletRequest request, ModelMap model, @RequestParam(value="floorId") String floorId) {
        model.addAttribute("floor", floorManager.getFloor(floorId));
        request.getSession().setAttribute("context", "appadmin/");
        return "appadmin/roomList";
    }
}
