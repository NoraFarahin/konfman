package com.jmw.konfman.dao.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.jmw.konfman.dao.AuthorityDao;
import com.jmw.konfman.model.Authority;


/**
 * This class interacts with Spring and Hibernate to save and
 * retrieve Building objects.
 *
 * @author Judah W
 */
@Repository(value = "authorityDao")
public class AuthorityDaoHibernate implements AuthorityDao {
    HibernateTemplate hibernateTemplate;
    Log logger = LogFactory.getLog(AuthorityDaoHibernate.class);
    
    @Autowired
    public AuthorityDaoHibernate(SessionFactory sessionFactory) {
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    public List getAuthorities() {
        return hibernateTemplate.loadAll(Authority.class);
    }

    public Authority getAuthority(Long id) {
        Authority user = (Authority) hibernateTemplate.get(Authority.class, id);
        if (user == null) {
            throw new ObjectRetrievalFailureException(Authority.class, id);
        }
        return user;
    }

    public void saveAuthority(Authority user) {
        hibernateTemplate.saveOrUpdate(user);

        if (logger.isDebugEnabled()) {
            logger.debug("userId set to: " + user.getId());
        }
    }

    public void removeAuthority(Long id) {
        hibernateTemplate.delete(getAuthority(id));
    }
}
