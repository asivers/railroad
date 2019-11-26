package railroad.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import railroad.dao.StationDAO;
import railroad.model.Station;

import java.util.*;

@Repository
@Transactional
public class StationDAOImpl implements StationDAO {

    private SessionFactory sessionFactory;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Gets number of stations by train number.
     *
     * @param trainNumber train number
     */
    @Override
    @SuppressWarnings("unchecked")
    public int countByTrainNumber(int trainNumber) {
        return sessionFactory.getCurrentSession()
                .createQuery("SELECT s.id FROM Train AS t INNER JOIN StationTrain AS st ON t.id = st.train_id INNER JOIN Station AS s ON st.station_id = s.id WHERE t.number = :trainNumber")
                .setParameter("trainNumber", trainNumber).list().size();
    }

    /**
     * Gets station's id list by train number.
     *
     * @param trainNumber train number
     * @param page page number
     * @param onPage number of passengers on the page
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Integer> getIdByTrainNumberList(int trainNumber, int page, int onPage) {
        return sessionFactory.getCurrentSession()
                .createQuery("SELECT s.id FROM Train AS t INNER JOIN StationTrain AS st ON t.id = st.train_id INNER JOIN Station AS s ON st.station_id = s.id WHERE t.number = :trainNumber ORDER BY st.time")
                .setParameter("trainNumber", trainNumber).setFirstResult(onPage * (page - 1)).setMaxResults(onPage).list();
    }

    /**
     * Gets station names list by train number.
     *
     * @param trainNumber train number
     * @param page page number
     * @param onPage number of passengers on the page
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<String> getStationNameByTrainNumberList(int trainNumber, int page, int onPage) {
        return sessionFactory.getCurrentSession()
                .createQuery("SELECT s.station_name FROM Train AS t INNER JOIN StationTrain AS st ON t.id = st.train_id INNER JOIN Station AS s ON st.station_id = s.id WHERE t.number = :trainNumber ORDER BY st.time")
                .setParameter("trainNumber", trainNumber).setFirstResult(onPage * (page - 1)).setMaxResults(onPage).list();
    }

    /**
     * Gets station's id by its name.
     *
     * @param stationName station name
     */
    @Override
    @SuppressWarnings("unchecked")
    public int getIdByStationNameSingle(String stationName) {
        return sessionFactory.getCurrentSession()
                .createQuery("SELECT s.id FROM Station AS s WHERE s.station_name = :stationName", Number.class)
                .setParameter("stationName", stationName).getSingleResult().intValue();
    }

    /**
     * Gets station name by id.
     *
     * @param id station's id
     */
    @Override
    @SuppressWarnings("unchecked")
    public String getStationNameByIdSingle(int id) {
        return sessionFactory.getCurrentSession()
                .createQuery("SELECT s.station_name FROM Station AS s WHERE s.id = :id")
                .setParameter("id", id).getSingleResult().toString();
    }

    /**
     * Gets number of stations by its id.
     * If station exists, the result will be 1.
     *
     * @param stationName station name
     */
    @Override
    @SuppressWarnings("unchecked")
    public int countByStationName(String stationName) {
        return sessionFactory.getCurrentSession()
                .createQuery("SELECT s.id FROM Station AS s WHERE s.station_name = :stationName")
                .setParameter("stationName", stationName).list().size();
    }

    /**
     * Adds new station to database.
     *
     * @param newStation new station
     */
    @Override
    @SuppressWarnings("unchecked")
    public void add(Station newStation) {
        sessionFactory.getCurrentSession().save(newStation);
    }

}