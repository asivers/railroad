package railroad.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import railroad.dao.TrainDAO;
import railroad.model.Train;

import java.sql.Time;
import java.util.*;

@Repository
public class TrainDAOImpl implements TrainDAO {

    private SessionFactory sessionFactory;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    @Override
    @SuppressWarnings("unchecked")
    public int countAllTrains() {
        return sessionFactory.getCurrentSession().createQuery("SELECT t.id FROM Train AS t", Number.class).list().size();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Integer> getTrainNumberList(int page, int onPage) {
        return sessionFactory.getCurrentSession().createQuery("SELECT t.number FROM Train AS t").setFirstResult(onPage * (page - 1)).setMaxResults(onPage).list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public int countByStationName(String stationName) {
        return sessionFactory.getCurrentSession().createQuery("SELECT t.id FROM Train AS t INNER JOIN StationTrain AS st ON t.id = st.train_id INNER JOIN Station AS s ON st.station_id = s.id WHERE s.station_name = :stationName", Number.class).setParameter("stationName", stationName).list().size();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Integer> getIdByStationNameList(String stationName, int page, int onPage) {
        return sessionFactory.getCurrentSession().createQuery("SELECT t.id FROM Train AS t INNER JOIN StationTrain AS st ON t.id = st.train_id INNER JOIN Station AS s ON st.station_id = s.id WHERE s.station_name = :stationName ORDER BY st.time").setParameter("stationName", stationName).setFirstResult(onPage * (page - 1)).setMaxResults(onPage).list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public int getTrainNumberByIdSingle(int id) {
        return sessionFactory.getCurrentSession().createQuery("SELECT t.number FROM Train AS t WHERE t.id = :id", Number.class).setParameter("id", id).getSingleResult().intValue();
    }

    @Override
    @SuppressWarnings("unchecked")
    public int countBySearch(String departureStationName, String arrivalStationName, Time lowerTime, Time upperTime) {
        return sessionFactory.getCurrentSession().createQuery("SELECT t.id FROM Train AS t INNER JOIN StationTrain AS st ON t.id = st.train_id INNER JOIN Station AS s ON st.station_id = s.id WHERE s.station_name = :departureStationName OR (s.station_name = :arrivalStationName AND st.time > :lowerTime AND st.time < :upperTime) GROUP BY t.id HAVING COUNT(*) = 2", Number.class).setParameter("departureStationName", departureStationName).setParameter("arrivalStationName", arrivalStationName).setParameter("lowerTime", lowerTime).setParameter("upperTime", upperTime).list().size();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Integer> getIdBySearchList(String departureStationName, String arrivalStationName, Time lowerTime, Time upperTime, int page, int onPage) {
        return sessionFactory.getCurrentSession().createQuery("SELECT t.id FROM Train AS t INNER JOIN StationTrain AS st ON t.id = st.train_id INNER JOIN Station AS s ON st.station_id = s.id WHERE s.station_name = :departureStationName OR (s.station_name = :arrivalStationName AND st.time > :lowerTime AND st.time < :upperTime) GROUP BY t.id HAVING COUNT(*) = 2 ORDER BY st.time").setParameter("departureStationName", departureStationName).setParameter("arrivalStationName", arrivalStationName).setParameter("lowerTime", lowerTime).setParameter("upperTime", upperTime).setFirstResult(onPage * (page - 1)).setMaxResults(onPage).list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public int getSeatsByIdSingle(int id) {
        return sessionFactory.getCurrentSession().createQuery("SELECT t.seats FROM Train AS t WHERE t.id = :trainID", Number.class).setParameter("trainID", id).getSingleResult().intValue();
    }

    @Override
    @SuppressWarnings("unchecked")
    public int countByTrainNumber(int trainNumber) {
        return sessionFactory.getCurrentSession().createQuery("SELECT t.id FROM Train AS t WHERE t.number = :trainNumber", Number.class).setParameter("trainNumber", trainNumber).list().size();
    }

    @Override
    @SuppressWarnings("unchecked")
    public int getIdByTrainNumberSingle(int trainNumber) {
        return sessionFactory.getCurrentSession().createQuery("SELECT t.id FROM Train AS t WHERE t.number = :trainNumber", Number.class).setParameter("trainNumber", trainNumber).getSingleResult().intValue();
    }

    @Override
    @SuppressWarnings("unchecked")
    public int getTrainNumberByTicketIdSingle(int id) {
        return sessionFactory.getCurrentSession().createQuery("SELECT t.number FROM Ticket AS i INNER JOIN Train AS t ON i.train_id = t.id WHERE i.id = :id", Number.class).setParameter("id", id).getSingleResult().intValue();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void add(Train newTrain) {
        sessionFactory.getCurrentSession().save(newTrain);
    }

}