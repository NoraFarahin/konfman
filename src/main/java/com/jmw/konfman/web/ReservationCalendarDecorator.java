package com.jmw.konfman.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.calendartag.decorator.DefaultCalendarDecorator;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContext;

import com.jmw.konfman.model.Reservation;
import com.jmw.konfman.model.User;

public class ReservationCalendarDecorator extends DefaultCalendarDecorator {
	int item = 0;
	SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
	
	public String getDay(String url) {
		
		//figure out if this is a user request or a room request
		String userId = this.pageContext.getRequest().getParameter("userId");
		String roomId = this.pageContext.getRequest().getParameter("roomId");
		String entity = null;
		if (userId != null){
			entity = "userId=" + userId;
		} else if (roomId != null){
			entity = "roomId=" + roomId;
		} else {
	    	SecurityContext ssc = (SecurityContext) this.pageContext.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
	    	Authentication auth = ssc.getAuthentication();
	    	User user = (User) auth.getPrincipal();
	    	entity = "userId=" + user.getId();
		}
		
		StringBuffer buffer = new StringBuffer();
		if (calendar.get(Calendar.DATE) != 1 ||
				(start.get(Calendar.MONTH) == end.get(Calendar.MONTH) &&
						start.get(Calendar.YEAR) == end.get(Calendar.YEAR))) {
			buffer.append("<a href=\"./cal-day.html?").append(entity).append("&date=").append(sdf.format(calendar.getTime())).append("\">").append(calendar.get(Calendar.DATE)).append("</a><br/>");
		} else {
			buffer.append("<a href=\"./cal-day.html?").append(entity).append("&date=").append(sdf.format(calendar.getTime())).append("\">").append(calendar.get(Calendar.DATE)).append("</a>")
			.append("<i>").append(months[calendar.get(Calendar.MONTH)]).append("</i><br/>");
		}
		List reservations = (List)pageContext.getRequest().getAttribute("reservations");
		if (reservations == null || reservations.size() == 0){
			return buffer.toString();
		}
		for(int i=item; i<reservations.size(); i++){
			
			//System.out.println("Cal time: " + calendar.getTime());
			Reservation reservation = (Reservation) reservations.get(i);
			if (reservation.getStartDateTime().after(calendar.getTime()) && reservation.getStartDateTime().before(new Date(calendar.getTimeInMillis() + 86400000))){
				item++;
				buffer.append(reservation.getStartTime()).append("<br/>");
			}
		}
			
		return buffer.toString();
	}
	
	public String getNextLink(String url) {
		String mUrl = url.replace(".jsp", ".html");
		return "<a href=\"" + mUrl + "\">>></a>";
	}
	
	public String getPreviousLink(String url) {
		String mUrl = url.replace(".jsp", ".html");
		return "<a href=\"" + mUrl + "\"><<</a>";
	}

}
