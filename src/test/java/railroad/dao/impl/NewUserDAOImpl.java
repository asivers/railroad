package railroad.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import railroad.dao.NewUserDAO;
import railroad.model.Authority;
import railroad.model.AuthorityUser;
import railroad.model.User;
import railroad.model.types.AuthorityType;

import java.util.HashSet;

@Repository
public class NewUserDAOImpl implements NewUserDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean isInDatabase(String username, String hashedPassword) {
        Session session = sessionFactory.getCurrentSession();
        int isNewUser = session.createQuery("SELECT u.id FROM User AS u WHERE u.username = :username", Number.class).setParameter("username", username).list().size();
        if (isNewUser > 0)
            return true;
        else {
            add(username, hashedPassword);
            int userID = session.createQuery("SELECT u.id FROM User AS u WHERE u.username = :username", Number.class).setParameter("username", username).getSingleResult().intValue();
            AuthorityUser newAuthorityUser = new AuthorityUser();
            newAuthorityUser.setAuthority_id(2);
            newAuthorityUser.setUser_id(userID);
            session.save(newAuthorityUser);
            return false;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void add(String username, String hashedPassword) {
        Session session = sessionFactory.getCurrentSession();
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(hashedPassword);
        session.save(newUser);
    }

}