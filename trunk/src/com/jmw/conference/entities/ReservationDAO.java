package com.jmw.conference.entities;

import java.util.List;

public class ReservationDAO extends AbstractDAO{

    public Reservation find(Integer id) {
        return (Reservation)super.find(Reservation.class, id);
    }
    
    public List findAll() {
        return super.findAll(Reservation.class);
    }
}
