package com.jmw.konfman.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jmw.konfman.service.BuildingManager;

@Controller
public class BuildingController {
    @Autowired
    BuildingManager buildingManager;

    @RequestMapping("/buildings.*")
    public String execute(ModelMap model) {
        model.addAttribute("buildingList", buildingManager.getBuildings());
        return "buildingList";
    }
}
