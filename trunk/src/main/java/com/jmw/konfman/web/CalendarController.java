package com.jmw.konfman.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jmw.konfman.model.Room;
import com.jmw.konfman.service.ReservationManager;
import com.jmw.konfman.service.RoomManager;

@Controller
public class CalendarController {
    @Autowired
    ReservationManager reservationManager;
    @Autowired
    RoomManager roomManager;

    /*
    @RequestMapping("/daily.*")
    public String execute(ModelMap model) {
        model.addAttribute("hours", );
        return "appadmin/buildingList";
    }*/
    
    @RequestMapping("/**/month-room.*")
    public String executeMonthRoom(ModelMap model, @RequestParam(value="roomId", required=true) String roomId, @RequestParam(value="date", required=true) String date){
    	Room room = roomManager.getRoom(roomId);
    	Date d = parseDate(date);
    	if (d == null){
    		d = new Date();
    	}
    	model.addAttribute(room);
    	model.addAttribute("reservation", reservationManager.getMonthlyRoomReservations(room, d));
    	return "monthly-room";
    }
    
    private Date parseDate(String date){
    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    	Date d = null;
    	try {
    		d = sdf.parse(date);
    	}catch (ParseException  pe){
    		
    	}
    	return d;
    }
}
