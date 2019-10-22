package railroad.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import railroad.dao.StationTrainDAO;
import railroad.model.StationTrain;
import java.util.*;

@Repository
public class StationTrainDAOImpl implements StationTrainDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<StationTrain> allStationTrains() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from StationTrain").list();
    }

    @Override
    public void add(StationTrain stationTrain) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(stationTrain);
    }

}