package com.jmw.konfman.web;

import java.util.Calendar;
import java.util.List;

import org.calendartag.decorator.DefaultCalendarDecorator;

import com.jmw.konfman.model.Reservation;

public class ReservationCalendarDecorator extends DefaultCalendarDecorator {
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
		for(int i=0; i<reservations.size(); i++){
			Reservation reservation = (Reservation) reservations.get(i);
			if (reservation.getStartDateTime().after(calendar.getTime())){
				buffer.append(reservation.getStartTime()).append("<br/>");
			}
		}
		
		
		return buffer.toString();
	}

}
