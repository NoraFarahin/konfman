package com.jmw.conference.entities;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

public class FloorDAO extends AbstractDAO{

    public Floor find(Integer id) {
        return (Floor)super.find(Floor.class, id);
    }
    
    public List findAll() {
        return super.findAll(Floor.class);
    }
    
    /*
    public List find(Building building){
        List objects = null;
        try {
            startOperation();
            Building b = (Building) session
            .createQuery("select b from Building b left join fetch p.events where p.id = :pid")
            .setParameter("pid", personId)
            .uniqueResult(); // Eager fetch the collection so we can use it detached

            objects = query.list();
            tx.commit();
        } catch (HibernateException e) {
            handleException(e);
        } finally {
            HibernateFactory.close(session);
        }
        return objects;
    }*/
}

