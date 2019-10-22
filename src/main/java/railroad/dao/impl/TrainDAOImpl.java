package railroad.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import railroad.dao.TrainDAO;
import railroad.model.Station;
import railroad.model.Train;
import java.util.*;

@Repository
public class TrainDAOImpl implements TrainDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Train> allTrains() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Train").list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Train> trainsByStation(Station station) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT Train.number, StationTrain.time FROM Train INNER JOIN StationTrain ON Train.id = StationTrain.train_id INNER JOIN Station ON Station.id = StationTrain.station_id WHERE Station.id = 1").list();
    }

    @Override
    public void add(Train train) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(train);
    }

    @Override
    public Train getById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Train.class, id);
    }
}