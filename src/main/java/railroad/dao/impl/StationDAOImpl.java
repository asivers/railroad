package railroad.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import railroad.dao.StationDAO;
import railroad.model.Station;
import java.util.*;

@Repository
public class StationDAOImpl implements StationDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Station> allStations() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Station").list();
    }

    @Override
    public void add(Station station) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(station);
    }

    @Override
    public Station getById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Station.class, id);
    }
}