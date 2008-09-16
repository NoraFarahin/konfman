package com.jmw.konfman.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jmw.konfman.model.User;
import com.jmw.konfman.service.UserManager;

@Controller
public class RoomAdminController {
    private final Log log = LogFactory.getLog(RoomAdminController.class);

    @Autowired
	UserManager userManager;

    @RequestMapping("/**/roomadmin*")
    public String execute(HttpServletRequest request, ModelMap model) {
    	SecurityContext ssc = (SecurityContext) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
    	Authentication auth = ssc.getAuthentication();
    	User user = (User) auth.getPrincipal();
    	log.debug("Found logged in user:" + user.toString());
    	User user1 = userManager.getUser(user.getId().toString());
    	log.debug("User controls " + user1.getAdministeredRooms().size() + " rooms");
        model.addAttribute("user", user1);
        request.getSession().setAttribute("context", "roomadmin/");
        return "/roomadmin";
    }
}
