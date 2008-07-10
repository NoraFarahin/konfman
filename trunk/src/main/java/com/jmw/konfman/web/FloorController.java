package com.jmw.konfman.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jmw.konfman.service.BuildingManager;

@Controller
public class FloorController {
    @Autowired
    BuildingManager buildingManager;

    @RequestMapping("/floors.*")
    public String execute(ModelMap model, @RequestParam(value="buildingId", required=false) String buildingId) {
        model.addAttribute("building", buildingManager.getBuilding(buildingId));
        return "floorList";
    }
}
