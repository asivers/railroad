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
    public List<TrainTime> trainsByStation(String stationName) {
        Session session = sessionFactory.getCurrentSession();

        List<Integer> trainNumbers = session.createQuery("SELECT t.number FROM Train AS t INNER JOIN StationTrain AS st ON t.id = st.train_id INNER JOIN Station AS s ON st.station_id = s.id WHERE s.station_name = :stationName").setParameter("stationName", stationName).list();

        List<Time> stopTimes = session.createQuery("SELECT st.time FROM Train AS t INNER JOIN StationTrain AS st ON t.id = st.train_id INNER JOIN Station AS s ON st.station_id = s.id WHERE s.station_name = :stationName").setParameter("stationName", stationName).list();
        List<Time> stopTimesUTC = new ArrayList<>();
        for (Time t : stopTimes) {
            stopTimesUTC.add(new Time(t.getTime() - 10800000));
        }

        String tmpStringTime = "";
        List<TrainTime> trainsTimes = new ArrayList<>();
        for (int i = 0; i < trainNumbers.size(); i++) {
            tmpStringTime = stopTimesUTC.get(i).toString();
            trainsTimes.add(new TrainTime(trainNumbers.get(i), tmpStringTime.substring(0, tmpStringTime.length() - 3)));
        }

        return trainsTimes;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TrainTimeTime> trainsBySearch(String departureStationName, String arrivalStationName, Time lowerTime, Time upperTime) {
        Session session = sessionFactory.getCurrentSession();

        List<Integer> trainIDs = session.createQuery("SELECT t.id FROM Train AS t INNER JOIN StationTrain AS st ON t.id = st.train_id INNER JOIN Station AS s ON st.station_id = s.id WHERE s.station_name = :departureStationName OR (s.station_name = :arrivalStationName AND st.time > :lowerTime AND st.time < :upperTime) GROUP BY t.id HAVING COUNT(*) = 2").setParameter("departureStationName", departureStationName).setParameter("arrivalStationName", arrivalStationName).setParameter("lowerTime", lowerTime).setParameter("upperTime", upperTime).list();
        List<TrainTimeTime> trainsBothTimes = new ArrayList<>();
        long tmpLong1 = 0;
        long tmpLong2 = 0;
        String tmpStringTime1 = "";
        String tmpStringTime2 = "";
        for (Integer id : trainIDs) {
            List<Integer> tmpNumber = session.createQuery("SELECT t.number FROM Train AS t WHERE t.id = :id").setParameter("id", id).list();
            List<Time> tmpDepartureTime = session.createQuery("SELECT st.time FROM Train AS t INNER JOIN StationTrain AS st ON t.id = st.train_id INNER JOIN Station AS s ON st.station_id = s.id WHERE t.id = :id AND s.station_name = :departureStationName").setParameter("id", id).setParameter("departureStationName", departureStationName).list();
            List<Time> tmpArrivalTime = session.createQuery("SELECT st.time FROM Train AS t INNER JOIN StationTrain AS st ON t.id = st.train_id INNER JOIN Station AS s ON st.station_id = s.id WHERE t.id = :id AND s.station_name = :arrivalStationName").setParameter("id", id).setParameter("arrivalStationName", arrivalStationName).list();
            tmpLong1 = tmpDepartureTime.get(0).getTime() - 10800000;
            tmpLong2 = tmpArrivalTime.get(0).getTime() - 10800000;
            if (tmpLong2 > tmpLong1) {
                tmpStringTime1 = (new Time(tmpLong1)).toString();
                tmpStringTime2 = (new Time(tmpLong2)).toString();
                trainsBothTimes.add(new TrainTimeTime(tmpNumber.get(0), tmpStringTime1.substring(0, tmpStringTime1.length() - 3), tmpStringTime2.substring(0, tmpStringTime2.length() - 3)));
            }
        }

        return trainsBothTimes;
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