package railroad.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import railroad.dao.UserDAO;
import railroad.model.User;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO {

    private SessionFactory sessionFactory;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public User getUserByUsernameSingle(String username) {
        return sessionFactory.getCurrentSession().createQuery("FROM User AS u WHERE u.username = :username", User.class).setParameter("username", username).uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public int countByUsername(String username) {
        return sessionFactory.getCurrentSession().createQuery("SELECT u.id FROM User AS u WHERE u.username = :username", Number.class).setParameter("username", username).list().size();
    }

    @Override
    @SuppressWarnings("unchecked")
    public int getIdByUsernameSingle(String username) {
        return sessionFactory.getCurrentSession().createQuery("SELECT u.id FROM User AS u WHERE u.username = :username", Number.class).setParameter("username", username).getSingleResult().intValue();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void add(User newUser) {
        sessionFactory.getCurrentSession().save(newUser);
    }
}
