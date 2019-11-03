package railroad.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import railroad.dao.TrainDAO;
import railroad.model.Train;
import railroad.model.additional.TrainTime;
import railroad.model.additional.TrainTimeTime;

import java.sql.Time;
import java.util.*;

import static railroad.dao.impl.TimeSupport.TimeToLong;

@Repository
public class TrainDAOImpl implements TrainDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public int allTrainsCount() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT t.id FROM Train AS t", Number.class).list().size();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Integer> allTrains(int page) {
        Session session = sessionFactory.getCurrentSession();
        int onPage = 7;
        List<Integer> allTrains = session.createQuery("SELECT t.number FROM Train AS t").setFirstResult(onPage * (page - 1)).setMaxResults(onPage).list();
        return allTrains;
    }

    @Override
    @SuppressWarnings("unchecked")
    public int trainsByStationCount(String stationName) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT t.id FROM Train AS t INNER JOIN StationTrain AS st ON t.id = st.train_id INNER JOIN Station AS s ON st.station_id = s.id WHERE s.station_name = :stationName", Number.class).setParameter("stationName", stationName).list().size();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TrainTime> trainsByStation(String stationName, int page) {
        Session session = sessionFactory.getCurrentSession();
        int onPage = 7;
        List<Integer> trainIDs = session.createQuery("SELECT t.id FROM Train AS t INNER JOIN StationTrain AS st ON t.id = st.train_id INNER JOIN Station AS s ON st.station_id = s.id WHERE s.station_name = :stationName ORDER BY st.time").setParameter("stationName", stationName).setFirstResult(onPage * (page - 1)).setMaxResults(onPage).list();
        List<TrainTime> trainsTimes = new ArrayList<>();
        String stopTime = "";
        for (Integer id : trainIDs) {
            int trainNumber = session.createQuery("SELECT t.number FROM Train AS t WHERE t.id = :id", Number.class).setParameter("id", id).getSingleResult().intValue();
            stopTime = session.createQuery("SELECT st.time FROM Train AS t INNER JOIN StationTrain AS st ON t.id = st.train_id INNER JOIN Station AS s ON st.station_id = s.id WHERE t.id = :id AND s.station_name = :stationName").setParameter("id", id).setParameter("stationName", stationName).getSingleResult().toString();
            stopTime = TimeSupport.LongToTime(TimeToLong(stopTime) - 10800000);
            trainsTimes.add(new TrainTime(trainNumber, stopTime));
        }
        return trainsTimes;
    }

    @Override
    @SuppressWarnings("unchecked")
    public int trainsBySearchCount(String departureStationName, String arrivalStationName, Time lowerTime, Time upperTime) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT t.id FROM Train AS t INNER JOIN StationTrain AS st ON t.id = st.train_id INNER JOIN Station AS s ON st.station_id = s.id WHERE s.station_name = :departureStationName OR (s.station_name = :arrivalStationName AND st.time > :lowerTime AND st.time < :upperTime) GROUP BY t.id HAVING COUNT(*) = 2", Number.class).setParameter("departureStationName", departureStationName).setParameter("arrivalStationName", arrivalStationName).setParameter("lowerTime", lowerTime).setParameter("upperTime", upperTime).list().size();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TrainTimeTime> trainsBySearch(String departureStationName, String arrivalStationName, Time lowerTime, Time upperTime, int page) {
        Session session = sessionFactory.getCurrentSession();
        int onPage = 7;
        List<Integer> trainIDs = session.createQuery("SELECT t.id FROM Train AS t INNER JOIN StationTrain AS st ON t.id = st.train_id INNER JOIN Station AS s ON st.station_id = s.id WHERE s.station_name = :departureStationName OR (s.station_name = :arrivalStationName AND st.time > :lowerTime AND st.time < :upperTime) GROUP BY t.id HAVING COUNT(*) = 2 ORDER BY st.time").setParameter("departureStationName", departureStationName).setParameter("arrivalStationName", arrivalStationName).setParameter("lowerTime", lowerTime).setParameter("upperTime", upperTime).setFirstResult(onPage * (page - 1)).setMaxResults(onPage).list();
        List<TrainTimeTime> trainsBothTimes = new ArrayList<>();
        String trueDepartureTime = "";
        String trueArrivalTime = "";
        for (Integer id : trainIDs) {
            int tmpNumber = session.createQuery("SELECT t.number FROM Train AS t WHERE t.id = :id", Number.class).setParameter("id", id).getSingleResult().intValue();
            trueDepartureTime = session.createQuery("SELECT st.time FROM Train AS t INNER JOIN StationTrain AS st ON t.id = st.train_id INNER JOIN Station AS s ON st.station_id = s.id WHERE t.id = :id AND s.station_name = :departureStationName").setParameter("id", id).setParameter("departureStationName", departureStationName).getSingleResult().toString();
            trueArrivalTime = session.createQuery("SELECT st.time FROM Train AS t INNER JOIN StationTrain AS st ON t.id = st.train_id INNER JOIN Station AS s ON st.station_id = s.id WHERE t.id = :id AND s.station_name = :arrivalStationName").setParameter("id", id).setParameter("arrivalStationName", arrivalStationName).getSingleResult().toString();
            if (TimeSupport.TimeToLong(trueArrivalTime) - 10800000 > TimeSupport.TimeToLong(trueDepartureTime) - 10800000)
                trainsBothTimes.add(new TrainTimeTime(tmpNumber, TimeSupport.LongToTime(TimeToLong(trueDepartureTime) - 10800000), TimeSupport.LongToTime(TimeToLong(trueArrivalTime) - 10800000)));
        }
        return trainsBothTimes;
    }

    @Override
    public boolean freeSeats(int trainNumber) {
        Session session = sessionFactory.getCurrentSession();
        int trainID = session.createQuery("SELECT t.id FROM Train AS t WHERE t.number = :trainNumber", Number.class).setParameter("trainNumber", trainNumber).getSingleResult().intValue();
        int tickets = session.createQuery("SELECT i.id FROM Train AS t INNER JOIN Ticket AS i ON t.id = i.train_id WHERE t.id = :trainID", Number.class).setParameter("trainID", trainID).list().size();
        int seats = session.createQuery("SELECT t.seats FROM Train AS t WHERE t.id = :trainID", Number.class).setParameter("trainID", trainID).getSingleResult().intValue();
        if (seats > tickets)
            return true;
        else
            return false;
    }

    @Override
    public boolean isExist(int trainNumber, int seats) {
        Session session = sessionFactory.getCurrentSession();
        int isNewTrain = session.createQuery("SELECT t.id FROM Train AS t WHERE t.number = :trainNumber", Number.class).setParameter("trainNumber", trainNumber).list().size();
        if (isNewTrain == 0) {
            add(trainNumber, seats);
            return false;
        }
        else
            return true;
    }

    @Override
    public void add(int trainNumber, int seats) {
        Session session = sessionFactory.getCurrentSession();
        Train newTrain = new Train();
        newTrain.setNumber(trainNumber);
        newTrain.setSeats(seats);
        session.save(newTrain);
    }

}