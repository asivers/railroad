package railroad.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import railroad.dao.StationDAO;
import railroad.model.Station;
import railroad.model.StationTrain;
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
    public int stationsByTrainCount(int trainNumber) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT COUNT(*) FROM Train AS t INNER JOIN StationTrain AS st ON t.id = st.train_id INNER JOIN Station AS s ON st.station_id = s.id WHERE t.number = :trainNumber", Number.class).setParameter("trainNumber", trainNumber).getSingleResult().intValue();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<StationTime> stationsByTrain(int trainNumber, int page) {
        Session session = sessionFactory.getCurrentSession();
        int onPage = 8;
        List<Integer> stationIDs = session.createQuery("SELECT s.id FROM Train AS t INNER JOIN StationTrain AS st ON t.id = st.train_id INNER JOIN Station AS s ON st.station_id = s.id WHERE t.number = :trainNumber").setParameter("trainNumber", trainNumber).setFirstResult(onPage * (page - 1)).setMaxResults(onPage).list();
        List<StationTime> stationsTimes = new ArrayList<>();
        String stopTime = "";
        for (Integer id : stationIDs) {
            String stationName = session.createQuery("SELECT s.station_name FROM Station AS s WHERE s.id = :id", String.class).setParameter("id", id).getSingleResult();
            stopTime = session.createQuery("SELECT st.time FROM Train AS t INNER JOIN StationTrain AS st ON t.id = st.train_id INNER JOIN Station AS s ON st.station_id = s.id WHERE s.id = :id AND t.number = :trainNumber").setParameter("id", id).setParameter("trainNumber", trainNumber).getSingleResult().toString();
            stopTime = TimeSupport.LongToTime(TimeToLong(stopTime) - 10800000);
            stationsTimes.add(new StationTime(stationName, stopTime));
        }
        return stationsTimes;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean isExist(String stationName) {
        Session session = sessionFactory.getCurrentSession();
        int isNewStation = session.createQuery("SELECT COUNT(*) FROM Station AS s WHERE s.station_name = :stationName", Number.class).setParameter("stationName", stationName).getSingleResult().intValue();
        if (isNewStation == 0) {
            add(stationName);
            return false;
        }
        else
            return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void add(String stationName) {
        Session session = sessionFactory.getCurrentSession();
        Station newStation = new Station();
        newStation.setStationName(stationName);
        session.save(newStation);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean isExistForTrain(int trainNumber, String stationName, String stopTime) {
        Session session = sessionFactory.getCurrentSession();
        int isNewStation = session.createQuery("SELECT COUNT(*) FROM Station AS s WHERE s.station_name = :stationName", Number.class).setParameter("stationName", stationName).getSingleResult().intValue();
        int stationID = 0;
        int trainID = session.createQuery("SELECT t.id FROM Train AS t WHERE t.number = :trainNumber", Number.class).setParameter("trainNumber", trainNumber).getSingleResult().intValue();
        if (isNewStation == 0) {
            add(stationName);
            stationID = session.createQuery("SELECT s.id FROM Station AS s WHERE s.station_name = :stationName", Number.class).setParameter("stationName", stationName).getSingleResult().intValue();
            StationTrain newStationTrain = new StationTrain();
            newStationTrain.setStationId(stationID);
            newStationTrain.setTrainId(trainID);
            newStationTrain.setTime(new Time(TimeSupport.TimeToLong(stopTime)));
            session.save(newStationTrain);
            return false;
        } else {
            stationID = session.createQuery("SELECT s.id FROM Station AS s WHERE s.station_name = :stationName", Number.class).setParameter("stationName", stationName).getSingleResult().intValue();
            int isByTrain = session.createQuery("SELECT COUNT(*) FROM StationTrain AS st WHERE st.station_id = :stationID AND st.train_id = :trainID", Number.class).setParameter("stationID", stationID).setParameter("trainID", trainID).getSingleResult().intValue();
            if (isByTrain == 0) {
                StationTrain newStationTrain = new StationTrain();
                newStationTrain.setStationId(stationID);
                newStationTrain.setTrainId(trainID);
                newStationTrain.setTime(new Time(TimeSupport.TimeToLong(stopTime)));
                session.save(newStationTrain);
                return false;
            }
            else
                return true;
        }
    }

}