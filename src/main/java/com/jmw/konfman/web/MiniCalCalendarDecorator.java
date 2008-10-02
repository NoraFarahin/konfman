package com.jmw.konfman.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.calendartag.decorator.DefaultCalendarDecorator;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContext;

import com.jmw.konfman.model.User;

public class MiniCalCalendarDecorator extends DefaultCalendarDecorator {
	int item = 0;
	SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
	
	public String getDay(String url) {
		String userId = pageContext.getRequest().getParameter("userId");
		String roomId = pageContext.getRequest().getParameter("roomId");
		String entity = null;
		if (userId != null){
			entity = "userId=" + userId;
		} else if (roomId != null){
			entity = "roomId=" + roomId;
		} else {
	    	SecurityContext ssc = (SecurityContext) pageContext.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
	    	Authentication auth = ssc.getAuthentication();
	    	User user = (User) auth.getPrincipal();
	    	entity = "userId=" + user.getId();
		}
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("<a href=\"./cal-day.html?").append(entity).append("&date=").append(sdf.format(calendar.getTime())).append("\">").append(calendar.get(Calendar.DATE)).append("</a><br/>");
		//buffer.append("<a href=\"./cal-day.html?userId=1&date=").append(sdf.format(calendar.getTime())).append("\">").append(calendar.get(Calendar.DATE)).append("</a><br/>");
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
