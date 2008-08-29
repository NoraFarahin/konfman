package com.jmw.konfman.dao.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.jmw.konfman.dao.UserDao;
import com.jmw.konfman.model.User;


/**
 * This class interacts with Spring and Hibernate to save and
 * retrieve User objects.
 *
 * @author Matt Raible
 */
@Repository(value = "userDao")
public class UserDaoHibernate implements UserDao {
    HibernateTemplate hibernateTemplate;
    Log logger = LogFactory.getLog(UserDaoHibernate.class);
    
    @Autowired
    public UserDaoHibernate(SessionFactory sessionFactory) {
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    public List getUsers() {
        return hibernateTemplate.find("from User");
    }

    public User getUser(Long id) {
        User user = (User) hibernateTemplate.get(User.class, id);
        if (user == null) {
            throw new ObjectRetrievalFailureException(User.class, id);
        }
        return user;
    }

    public User getUser(String username) {
    	logger.debug("Looking up user: " + username);
        List list = hibernateTemplate.find("from User where username = ?" , username);
        if (list.size() == 0){
        	return null;
        }
    	User user = (User) list.get(0);
        if (user == null) {
        	logger.debug("User not found: " + username);
            throw new ObjectRetrievalFailureException(User.class, username);
        }
        return user;
    }

    public void saveUser(User user) {
        hibernateTemplate.saveOrUpdate(user);

        if (logger.isDebugEnabled()) {
            logger.debug("userId set to: " + user.getId());
        }
    }

    public void removeUser(Long id) {
        hibernateTemplate.delete(getUser(id));
    }
}
