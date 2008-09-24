package com.jmw.konfman.web;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.calendartag.decorator.DefaultCalendarDecorator;

import com.jmw.konfman.model.Reservation;

public class ReservationCalendarDecorator extends DefaultCalendarDecorator {
	int item = 0;
	
	public String getDay(String url) {
		StringBuffer buffer = new StringBuffer();
		if (calendar.get(Calendar.DATE) != 1 ||
				(start.get(Calendar.MONTH) == end.get(Calendar.MONTH) &&
						start.get(Calendar.YEAR) == end.get(Calendar.YEAR))) {
			buffer.append("<a href=\"./daily.jsp\">").append(calendar.get(Calendar.DATE)).append("</a><br/>");
		} else {
			buffer.append("<a href=\"").append(url).append("\">").append(calendar.get(Calendar.DATE)).append("</a>")
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
