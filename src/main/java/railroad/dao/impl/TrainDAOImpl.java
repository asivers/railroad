package railroad.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import railroad.dao.TrainDAO;
import railroad.model.Train;

import java.sql.Time;
import java.util.*;

@Repository
@Transactional
public class TrainDAOImpl implements TrainDAO {

    private SessionFactory sessionFactory;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    /**
     * Gets number of all trains in the database.
     */
    @Override
    @SuppressWarnings("unchecked")
    public int countAllTrains() {
        return sessionFactory.getCurrentSession().createQuery("SELECT t.id FROM Train AS t").list().size();
    }

    /**
     * Gets list of all trains in the database.
     * @param page page number
     * @param onPage number of trains on the page
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Integer> getTrainNumberList(int page, int onPage) {
        return sessionFactory.getCurrentSession().createQuery("SELECT t.number FROM Train AS t").setFirstResult(onPage * (page - 1)).setMaxResults(onPage).list();
    }

    /**
     * Gets number of trains by station name.
     *
     * @param stationName station name
     */
    @Override
    @SuppressWarnings("unchecked")
    public int countByStationName(String stationName) {
        return sessionFactory.getCurrentSession().createQuery("SELECT t.id FROM Train AS t INNER JOIN StationTrain AS st ON t.id = st.train_id INNER JOIN Station AS s ON st.station_id = s.id WHERE s.station_name = :stationName").setParameter("stationName", stationName).list().size();
    }

    /**
     * Gets train numbers list by station name.
     *
     * @param stationName station name
     * @param page page number
     * @param onPage number of trains on the page
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Integer> getIdByStationNameList(String stationName, int page, int onPage) {
        return sessionFactory.getCurrentSession().createQuery("SELECT t.id FROM Train AS t INNER JOIN StationTrain AS st ON t.id = st.train_id INNER JOIN Station AS s ON st.station_id = s.id WHERE s.station_name = :stationName ORDER BY st.time").setParameter("stationName", stationName).setFirstResult(onPage * (page - 1)).setMaxResults(onPage).list();
    }

    /**
     * Gets train number by id.
     *
     * @param id train's id
     */
    @Override
    @SuppressWarnings("unchecked")
    public int getTrainNumberByIdSingle(int id) {
        return sessionFactory.getCurrentSession().createQuery("SELECT t.number FROM Train AS t WHERE t.id = :id", Number.class).setParameter("id", id).getSingleResult().intValue();
    }

    /**
     * Gets number of trains which go to arrival station and arrive
     * not earlier than lower time but not later than upper time.
     *
     * @param arrivalStationName arrival station
     * @param lowerTime lower time
     * @param upperTime upper time
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Integer> getIdByArrivalSearchList(String arrivalStationName, Time lowerTime, Time upperTime) {
        return sessionFactory.getCurrentSession().createQuery("SELECT t.id FROM Train AS t INNER JOIN StationTrain AS st ON t.id = st.train_id INNER JOIN Station AS s ON st.station_id = s.id WHERE s.station_name = :arrivalStationName AND st.time > :lowerTime AND st.time < :upperTime").setParameter("arrivalStationName", arrivalStationName).setParameter("lowerTime", lowerTime).setParameter("upperTime", upperTime).list();
    }

    /**
     * Gets number of trains which go to departure station and arrive
     * there not later than they arrive to arrival station.
     * If the train is appropriate, the result will be 1.
     *
     * @param train_id train's id
     * @param departureStationName departure station
     * @param untilTime time until which the train must arrive to arrival station.
     */
    @Override
    @SuppressWarnings("unchecked")
    public int isAppropriateBySearch(int train_id, String departureStationName, Time untilTime) {
        return sessionFactory.getCurrentSession().createQuery("SELECT t.id FROM Train AS t INNER JOIN StationTrain AS st ON t.id = st.train_id INNER JOIN Station AS s ON st.station_id = s.id WHERE t.id = :train_id AND s.station_name = :departureStationName AND st.time < :untilTime").setParameter("train_id", train_id).setParameter("departureStationName", departureStationName).setParameter("untilTime", untilTime).list().size();
    }

    /**
     * Gets train's id list for trains which go from departure station to arrival
     * station and arrive not earlier than lower time but not later than upper time.
     *
     * @param departureStationName departure station
     * @param arrivalStationName arrival station
     * @param lowerTime lower time
     * @param upperTime upper time
     * @param page page number
     * @param onPage number of trains on the page
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Integer> getIdBySearchList(String departureStationName, String arrivalStationName, Time lowerTime, Time upperTime, int page, int onPage) {
        return sessionFactory.getCurrentSession().createQuery("SELECT t.id FROM Train AS t INNER JOIN StationTrain AS st ON t.id = st.train_id INNER JOIN Station AS s ON st.station_id = s.id WHERE s.station_name = :departureStationName OR (s.station_name = :arrivalStationName AND st.time > :lowerTime AND st.time < :upperTime) ORDER BY st.time").setParameter("departureStationName", departureStationName).setParameter("arrivalStationName", arrivalStationName).setParameter("lowerTime", lowerTime).setParameter("upperTime", upperTime).setFirstResult(onPage * (page - 1)).setMaxResults(onPage).list();
    }

    /**
     * Gets number of seats by train's id.
     *
     * @param id train's id
     */
    @Override
    @SuppressWarnings("unchecked")
    public int getSeatsByIdSingle(int id) {
        return sessionFactory.getCurrentSession().createQuery("SELECT t.seats FROM Train AS t WHERE t.id = :trainID", Number.class).setParameter("trainID", id).getSingleResult().intValue();
    }

    /**
     * Gets number of trains by its id.
     * If train exists, the result will be 1.
     *
     * @param trainNumber train number
     */
    @Override
    @SuppressWarnings("unchecked")
    public int countByTrainNumber(int trainNumber) {
        return sessionFactory.getCurrentSession().createQuery("SELECT t.id FROM Train AS t WHERE t.number = :trainNumber").setParameter("trainNumber", trainNumber).list().size();
    }

    /**
     * Gets train's id by its number.
     *
     * @param trainNumber train number
     */
    @Override
    @SuppressWarnings("unchecked")
    public int getIdByTrainNumberSingle(int trainNumber) {
        return sessionFactory.getCurrentSession().createQuery("SELECT t.id FROM Train AS t WHERE t.number = :trainNumber", Number.class).setParameter("trainNumber", trainNumber).getSingleResult().intValue();
    }

    /**
     * Gets train number by ticket's id.
     *
     * @param id ticket's id
     */
    @Override
    @SuppressWarnings("unchecked")
    public int getTrainNumberByTicketIdSingle(int id) {
        return sessionFactory.getCurrentSession().createQuery("SELECT t.number FROM Ticket AS i INNER JOIN Train AS t ON i.train_id = t.id WHERE i.id = :id", Number.class).setParameter("id", id).getSingleResult().intValue();
    }

    /**
     * Deletes train by its number.
     *
     * @param trainNumber train number
     */
    @Override
    @SuppressWarnings("unchecked")
    public void deleteByTrainNumber(int trainNumber) {
        sessionFactory.getCurrentSession().createQuery("DELETE FROM Train AS t WHERE t.number = :trainNumber").setParameter("trainNumber", trainNumber).executeUpdate();
    }

    /**
     * Adds new train to database.
     *
     * @param newTrain new train
     */
    @Override
    @SuppressWarnings("unchecked")
    public void add(Train newTrain) {
        sessionFactory.getCurrentSession().save(newTrain);
    }

}