package railroad.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import railroad.dao.StationDAO;
import railroad.model.Station;
import railroad.model.additional.StationTime;
import railroad.model.additional.TrainTime;

import java.sql.Time;
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
    @SuppressWarnings("unchecked")
    public List<StationTime> stationsByTrain(int trainNumber) {
        Session session = sessionFactory.getCurrentSession();

        List<String> stationNames = session.createQuery("SELECT s.station_name FROM Train AS t INNER JOIN StationTrain AS st ON t.id = st.train_id INNER JOIN Station AS s ON st.station_id = s.id WHERE t.number = :trainNumber").setParameter("trainNumber", trainNumber).list();

        List<Time> stopTimes = session.createQuery("SELECT st.time FROM Train AS t INNER JOIN StationTrain AS st ON t.id = st.train_id INNER JOIN Station AS s ON st.station_id = s.id WHERE t.number = :trainNumber").setParameter("trainNumber", trainNumber).list();
        List<Time> stopTimesUTC = new ArrayList<>();
        for (Time t : stopTimes) {
            stopTimesUTC.add(new Time(t.getTime() - 10800000));
        }

        String tmpStringTime = "";
        List<StationTime> stationsTimes = new ArrayList<>();
        for (int i = 0; i < stationNames.size(); i++) {
            tmpStringTime = stopTimesUTC.get(i).toString();
            stationsTimes.add(new StationTime(stationNames.get(i), tmpStringTime.substring(0, tmpStringTime.length() - 3)));
        }

        return stationsTimes;
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