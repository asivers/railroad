package railroad.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import railroad.dao.TrainDAO;
import railroad.model.Station;
import railroad.model.StationTrain;
import railroad.model.Train;
import railroad.model.TrainTime;

import java.sql.Time;
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
//    public List<TrainTime> trainsByStation(String stationName) {
    public List<Integer> trainsByStation(String stationName) {
        Session session = sessionFactory.getCurrentSession();

        List<Integer> trainNumbers = session.createQuery("SELECT t.number FROM Train as t INNER JOIN StationTrain as st ON t.id = st.train_id INNER JOIN Station as s ON st.station_id = s.id WHERE s.station_name = :stationName").setParameter("stationName", stationName).list();

        List<Time> stopTimes = session.createQuery("SELECT st.time FROM Train as t INNER JOIN StationTrain as st ON t.id = st.train_id INNER JOIN Station as s ON st.station_id = s.id WHERE s.station_name = :stationName").setParameter("stationName", stationName).list();
        List<Time> stopTimesUTC = new ArrayList<>();
        for (Time t : stopTimes) {
            stopTimesUTC.add(new Time(t.getTime() - 10800000));
        }

        List<TrainTime> trainsTimes = new ArrayList<>();
        for (int i = 0; i < trainNumbers.size(); i++)
            trainsTimes.add(new TrainTime(trainNumbers.get(i), stopTimesUTC.get(i)));

        for (int i = 0; i < trainsTimes.size(); i++) {
            System.out.println(trainsTimes.get(i).getNumber());
            System.out.println(trainsTimes.get(i).getTime());
            System.out.println();
        }



        return session.createQuery("SELECT Train.number FROM Train").list();
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