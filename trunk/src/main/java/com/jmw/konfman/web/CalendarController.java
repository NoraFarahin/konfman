package com.jmw.konfman.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContext;
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
    
    private User getUser(HttpServletRequest request){
    	SecurityContext ssc = (SecurityContext) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
    	Authentication auth = ssc.getAuthentication();
    	return (User) auth.getPrincipal();
    }
    
    @RequestMapping("/**/cal-month.*")
    public String executeMonthRoom(HttpServletRequest request, ModelMap model, @RequestParam(value="roomId", required=false) String roomId, @RequestParam(value="userId", required=false) String userId, @RequestParam(value="date", required=false) String date){
    	Room room = null;
    	User user = null;
    	if (userId != null){
    		user = userManager.getUser(userId);
    	} else if (roomId != null){
    		room = roomManager.getRoom(roomId);
    	} else {
    		user = getUser(request);
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
    public String executeWeekRoom(HttpServletRequest request, ModelMap model, @RequestParam(value="roomId", required=false) String roomId, @RequestParam(value="userId", required=false) String userId, @RequestParam(value="date", required=false) String date){
    	Room room = null;
    	User user = null;
    	if (userId != null){
    		user = userManager.getUser(userId);
    	} else if (roomId != null){
    		room = roomManager.getRoom(roomId);
    	} else {
    		user = getUser(request);
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
    public String executeDay(HttpServletRequest request, ModelMap model, @RequestParam(value="roomId", required=false) String roomId, @RequestParam(value="userId", required=false) String userId, @RequestParam(value="date", required=false) String date){
    	Room room = null;
    	User user = null;
    	String entityId = "";
    	if (userId != null && ! userId.equals("")){
    		user = userManager.getUser(userId);
    		entityId = "&userId=" + user.getId();
    	} else if (roomId != null && ! roomId.equals("")){
    		room = roomManager.getRoom(roomId);
    		entityId = "&roomId=" + room.getId();
    	} else {
    		user = getUser(request);
    		entityId = "&userId=" + user.getId();
    	}
    	
    	DateTime now = null;
    	if (date == null){
    		now = new DateTime();
    		now = now.minuteOfDay().setCopy(0);
    	} else {
    		now = DateTimeFormat.forPattern("MM-dd-yyyy").parseDateTime(date);
    	}
    	
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
        			String datetime = now.toString("yyyy-MM-dd:hh:mm:a");
        			Reservation newRes = new Reservation();
        			newRes.setComment("<a href=\"./reservationform.html?dest=cal-day.html&date=" + datetime + entityId +"\">Reserve this slot</a>");
        			hours.add(new Hour(now.toString("h:mm"), newRes));
        		}
    		} else {
    			String datetime = now.toString("yyyy-MM-dd:hh:mm:a");
    			Reservation newRes = new Reservation();
    			newRes.setComment("<a href=\"./reservationform.html?dest=cal-day.html&date=" + datetime + entityId +"\">Reserve this slot</a>");
    			hours.add(new Hour(now.toString("h:mm"), newRes));
    		}
        	now = now.plusMinutes(30);
    	}
    	
    	//create next link
    	StringBuffer next = new StringBuffer();
    	next.append("<a href=\"./cal-day.html?date=");
    	next.append(now.plusDays(1).toString("MM-dd-yyyy"));
    	if (user != null){
    		next.append("&userId=").append(user.getId());
    	} else {
    		next.append("&roomId=").append(roomId);
    	}
    	next.append("\">&gt;&gt;</a>");
    	model.addAttribute("next", next.toString());
    	

    	//create previous link
    	StringBuffer prev = new StringBuffer();
    	prev.append("<a href=\"./cal-day.html?date=");
    	prev.append(now.plusDays(-1).toString("MM-dd-yyyy"));
    	if (user != null){
    		prev.append("&userId=").append(user.getId());
    	} else {
    		prev.append("&roomId=").append(roomId);
    	}
    	prev.append("\">&lt;&lt;</a>");
    	model.addAttribute("prev", prev.toString());

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
