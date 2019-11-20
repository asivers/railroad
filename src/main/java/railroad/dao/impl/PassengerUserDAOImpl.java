package railroad.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import railroad.dao.PassengerUserDAO;
import railroad.model.PassengerUser;

@Repository
@Transactional
public class PassengerUserDAOImpl implements PassengerUserDAO {

    private SessionFactory sessionFactory;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void add(PassengerUser newPassengerUser) {
        sessionFactory.getCurrentSession().save(newPassengerUser);
    }

    @Override
    @SuppressWarnings("unchecked")
    public int countRelations(int passenger_id, int user_id) {
        return sessionFactory.getCurrentSession().createQuery("SELECT pu.user_id FROM PassengerUser AS pu WHERE pu.passenger_id = :passengerID AND pu.user_id = :currentUserID", Number.class).setParameter("passengerID", passenger_id).setParameter("currentUserID", user_id).list().size();
    }

}
