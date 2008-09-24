package com.jmw.konfman.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
    public String executeMonthRoom(ModelMap model, @RequestParam(value="roomId", required=false) String roomId, @RequestParam(value="userId", required=false) String userId, @RequestParam(value="date", required=true) String date){
    	Room room = null;
    	User user = null;
    	if (roomId != null){
    		room = roomManager.getRoom(roomId);
    	}
    	if (userId != null){
    		user = userManager.getUser(userId);
    	}
    	
    	Date d = parseDate(date);
    	if (d == null){
    		d = new Date();
    	}
    	List list = null;
    	if (room != null){
    		model.addAttribute("entity", room);
    		list = reservationManager.getMonthlyRoomReservations(room, d);
    	} else {
    		model.addAttribute("entity", user);
    		list = reservationManager.getMonthlyUserReservations(user, d);
    	}
    	log.debug("Retrived: " + list.size() + " items for the calendar");
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
    	
    	Date d = parseDate(date);
    	if (d == null){
    		d = new Date();
    	}
    	List list = null;
    	if (room != null){
    		model.addAttribute("entity", room);
    		list = reservationManager.getWeeklyRoomReservations(room, d);
    	} else {
    		model.addAttribute("entity", user);
    		list = reservationManager.getWeeklyUserReservations(user, d);
    	}
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(d);
    	cal.set(Calendar.DAY_OF_WEEK, 1);
    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    	model.addAttribute("startDate", sdf.format(cal.getTime()));
    	
    	cal.set(Calendar.DAY_OF_WEEK, 7);
    	model.addAttribute("endDate", sdf.format(cal.getTime()));

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
    	
    	Date d = parseDate(date);
    	if (d == null){
    		d = new Date();
    	}
    	List list = null;
    	if (room != null){
    		model.addAttribute("entity", room);
    		list = reservationManager.getDailyRoomReservations(room, d);
    	} else {
    		model.addAttribute("entity", user);
    		list = reservationManager.getDailyUserReservations(user, d);
    	}
    	model.addAttribute("date", d.toString());
    	DateFormat df = DateFormat.getTimeInstance(DateFormat.SHORT);
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(d);
    	cal.set(Calendar.HOUR_OF_DAY, 8);
    	List hours = new ArrayList();
    	Iterator iter = list.iterator();
    	Reservation res = null;
    	for(int i=0; i<20; i++){
    		if (iter.hasNext()){
    			res = (Reservation) iter.next();
    		}
    		Date displayNow = new Date(cal.getTimeInMillis() - 1);
    		Date now = new Date(cal.getTimeInMillis() + 1);
    		cal.add(Calendar.MINUTE, 30);
    		Date later = cal.getTime();
    		if (res != null && (now.after(res.getStartDateTime()) && now.before(res.getEndDateTime()))){
    			hours.add(new Hour(df.format(displayNow), res));
    		} else {
    			hours.add(new Hour(df.format(displayNow), new Reservation()));
    		}
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
