/**
 * 
 */
package com.jmw.conference.entities;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;

import java.util.List;
/**
 * @author judahw
 *
 */
public class AbstractDAO {

	    protected Session session;
	    protected Transaction tx;

	    public AbstractDAO() {
	        HibernateFactory.buildIfNeeded();
	    }

	    public void saveOrUpdate(Object obj) {
	        try {
	            startOperation();
	            session.saveOrUpdate(obj);
	            tx.commit();
	        } catch (HibernateException e) {
	            handleException(e);
	        } finally {
	            HibernateFactory.close(session);
	        }
	    }

	    public void delete(Object obj) {
	        try {
	            startOperation();
	            session.delete(obj);
	            tx.commit();
	        } catch (HibernateException e) {
	            handleException(e);
	        } finally {
	            HibernateFactory.close(session);
	        }
	    }

	    public Object find(Class clazz, Integer id) {
	        Object obj = null;
	        try {
	            startOperation();
	            obj = session.load(clazz, id);
	            tx.commit();
	        } catch (HibernateException e) {
	            handleException(e);
	        } finally {
	            HibernateFactory.close(session);
	        }
	        return obj;
	    }

	    public List findAll(Class clazz) {
	        List objects = null;
	        try {
	            startOperation();
	            Query query = session.createQuery("from " + clazz.getName());
	            objects = query.list();
	            tx.commit();
	        } catch (HibernateException e) {
	            handleException(e);
	        } finally {
	            HibernateFactory.close(session);
	        }
	        return objects;
	    }

	    protected void handleException(HibernateException e) throws DataAccessLayerException {
	        HibernateFactory.rollback(tx);
	        throw new DataAccessLayerException(e);
	    }

	    protected void startOperation() throws HibernateException {
	        session = HibernateFactory.openSession();
	        tx = session.beginTransaction();
	    }

}
