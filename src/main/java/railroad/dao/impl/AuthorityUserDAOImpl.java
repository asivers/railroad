package railroad.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import railroad.dao.AuthorityUserDAO;
import railroad.model.AuthorityUser;

@Repository
@Transactional
public class AuthorityUserDAOImpl implements AuthorityUserDAO {

    private SessionFactory sessionFactory;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Adds authority-user relation to database.
     *
     * @param newAuthorityUser new authority-user relation
     */
    @Override
    @SuppressWarnings("unchecked")
    public void add(AuthorityUser newAuthorityUser) {
        sessionFactory.getCurrentSession().save(newAuthorityUser);
    }

}
