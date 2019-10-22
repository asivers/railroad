package railroad.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import railroad.dao.PassengerDAO;
import railroad.model.Passenger;
import java.util.*;

@Repository
public class PassengerDAOImpl implements PassengerDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Passenger> allPassengers() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Passenger").list();
    }

    @Override
    public void add(Passenger passenger) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(passenger);
    }

    @Override
    public Passenger getById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Passenger.class, id);
    }
}