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

import static railroad.dao.impl.TimeSupport.TimeToLong;

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
    public int stationsByTrainCount(int trainNumber) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT COUNT(*) FROM Train AS t INNER JOIN StationTrain AS st ON t.id = st.train_id INNER JOIN Station AS s ON st.station_id = s.id WHERE t.number = :trainNumber", Number.class).setParameter("trainNumber", trainNumber).getSingleResult().intValue();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<StationTime> stationsByTrain(int trainNumber, int page) {
        Session session = sessionFactory.getCurrentSession();
        int onPage = 10;
        List<Integer> stationIDs = session.createQuery("SELECT s.id FROM Train AS t INNER JOIN StationTrain AS st ON t.id = st.train_id INNER JOIN Station AS s ON st.station_id = s.id WHERE t.number = :trainNumber").setParameter("trainNumber", trainNumber).setFirstResult(onPage * (page - 1)).setMaxResults(onPage).list();
        List<StationTime> stationsTimes = new ArrayList<>();
        String trueStopTime = "";
        for (Integer id : stationIDs) {
            String stationName = session.createQuery("SELECT s.station_name FROM Station AS s WHERE s.id = :id", String.class).setParameter("id", id).getSingleResult();
            String stopTime = session.createQuery("SELECT st.time FROM Train AS t INNER JOIN StationTrain AS st ON t.id = st.train_id INNER JOIN Station AS s ON st.station_id = s.id WHERE s.id = :id AND t.number = :trainNumber").setParameter("id", id).setParameter("trainNumber", trainNumber).getSingleResult().toString();
            stopTime = TimeSupport.LongToTime(TimeToLong(stopTime) - 10800000);
            stationsTimes.add(new StationTime(stationName, stopTime));
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