package com.jmw.konfman.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jmw.konfman.model.Hour;
import com.jmw.konfman.model.Reservation;
import com.jmw.konfman.model.Room;
import com.jmw.konfman.model.User;
import com.jmw.konfman.service.ReservationManager;
import com.jmw.konfman.service.RoomManager;
import com.jmw.konfman.service.UserManager;

@Controller
public class CalendarController {
    private final Log log = LogFactory.getLog(CalendarController.class);
    @Autowired
    ReservationManager reservationManager;
    @Autowired
    UserManager userManager;
    @Autowired
    RoomManager roomManager;

    /*
    @RequestMapping("/daily.*")
    public String execute(ModelMap model) {
        model.addAttribute("hours", );
        return "appadmin/buildingList";
    }*/
    
    @RequestMapping("/**/cal-month.*")
    public String executeMonthRoom(ModelMap model, @RequestParam(value="roomId", required=false) String roomId, @RequestParam(value="userId", required=false) String userId, @RequestParam(value="date", required=false) String date){
    	Room room = null;
    	User user = null;
    	if (roomId != null){
    		room = roomManager.getRoom(roomId);
    	}
    	if (userId != null){
    		user = userManager.getUser(userId);
    	}
    	
    	DateTime dt = null;
    	if (date == null){
    		dt = new DateTime();
    	} else {
    		dt = DateTimeFormat.forPattern("MM-dd-yyyy").parseDateTime(date);
    	}

    	List list = null;
    	if (room != null){
    		model.addAttribute("entity", room);
    		list = reservationManager.getMonthlyRoomReservations(room, dt.toDate());
    	} else {
    		model.addAttribute("entity", user);
    		list = reservationManager.getMonthlyUserReservations(user, dt.toDate());
    	}
    	log.debug("Retrived: " + list.size() + " items for the calendar");
    	model.addAttribute("month", dt.toString("MMMM"));
    	model.addAttribute("reservations", list);
    	return "cal-month";
    }
    
    @RequestMapping("/**/cal-week.*")
    public String executeWeekRoom(ModelMap model, @RequestParam(value="roomId", required=false) String roomId, @RequestParam(value="userId", required=false) String userId, @RequestParam(value="date", required=true) String date){
    	Room room = null;
    	User user = null;
    	if (roomId != null){
    		room = roomManager.getRoom(roomId);
    	}
    	if (userId != null){
    		user = userManager.getUser(userId);
    	}
    	
    	DateTime dt = DateTimeFormat.forPattern("MM-dd-yyyy").parseDateTime(date);
    	List list = null;
    	if (room != null){
    		model.addAttribute("entity", room);
    		list = reservationManager.getWeeklyRoomReservations(room, dt.toDate());
    	} else {
    		model.addAttribute("entity", user);
    		list = reservationManager.getWeeklyUserReservations(user, dt.toDate());
    	}
    	dt = dt.dayOfWeek().setCopy(1);
    	dt = dt.minusDays(1);
    	model.addAttribute("startDate", dt.toString("MM/dd/yyyy"));
    	dt = dt.plusDays(6);
    	model.addAttribute("endDate", dt.toString("MM/dd/yyyy"));

    	log.debug("Retrived: " + list.size() + " items for the calendar");
    	model.addAttribute("reservations", list);
    	return "cal-week";
    }
    
    @RequestMapping("/**/cal-day.*")
    public String executeDay(ModelMap model, @RequestParam(value="roomId", required=false) String roomId, @RequestParam(value="userId", required=false) String userId, @RequestParam(value="date", required=true) String date){
    	Room room = null;
    	User user = null;
    	if (roomId != null){
    		room = roomManager.getRoom(roomId);
    	}
    	if (userId != null){
    		user = userManager.getUser(userId);
    	}
    	
    	DateTime now = DateTimeFormat.forPattern("MM-dd-yyyy").parseDateTime(date);

    	List list = null;
    	if (room != null){
    		model.addAttribute("entity", room);
    		list = reservationManager.getDailyRoomReservations(room, now.toDate());
    	} else {
    		model.addAttribute("entity", user);
    		list = reservationManager.getDailyUserReservations(user, now.toDate());
    	}
    	model.addAttribute("date", now.toString("MMMM d, yyyy"));
    	now = now.plusHours(8);
    	List hours = new ArrayList();
    	Iterator iter = list.iterator();
    	Reservation res = null;
    	for(int i=0; i<20; i++){
    		if (res == null && iter.hasNext()){
    			res = (Reservation) iter.next();
    		}
        	Interval interval = new Interval(now, now.plusMinutes(30));
        	if (res != null){
        		Interval resInterval = new Interval(res.getStartDateTime().getTime(), res.getEndDateTime().getTime());
        		if (interval.overlaps(resInterval)){
        			hours.add(new Hour(now.toString("h:mm"), res));
        			if (!resInterval.contains(now.plusMinutes(30))){
        				res = null;
        			}
        		} else {
        			hours.add(new Hour(now.toString("h:mm"), new Reservation()));
        		}
    		} else {
    			hours.add(new Hour(now.toString("h:mm"), new Reservation()));
    		}
        	now = now.plusMinutes(30);
    	}

    	log.debug("Retrived: " + list.size() + " items for the calendar");
    	model.addAttribute("hours", hours);
    	return "cal-day";
    }
    
    private Date parseDate(String date){
    	SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
    	Date d = null;
    	try {
    		d = sdf.parse(date);
    	}catch (ParseException  pe){
    		log.warn("Unable to parse date supplied");
    	}
    	return d;
    }
}
