package com.jmw.conference.entities;

import java.util.*;

public class BuildingDAO extends AbstractDAO{

    public Building find(Integer id) {
        return (Building)super.find(Building.class, id);
    }
    
    public List findAll() {
        return super.findAll(Building.class);
    }
}
