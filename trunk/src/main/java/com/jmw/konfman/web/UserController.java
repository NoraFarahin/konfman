package com.jmw.konfman.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jmw.konfman.service.UserManager;

@Controller
public class UserController {
    @Autowired
    UserManager userManager;

    @RequestMapping("/userlist.*")
    public String execute(ModelMap model) {
        model.addAttribute("userList", userManager.getUsers());
        return "userList";
    }
    @RequestMapping("/usersselect.*")
    public String executeSelect(ModelMap model) {
    	System.out.println("userselect");
        model.addAttribute("userList", userManager.getUsers());
        return "usersSelect";
    }
    
}
