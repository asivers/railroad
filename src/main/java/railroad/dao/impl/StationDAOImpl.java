package railroad.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import railroad.dao.StationDAO;
import railroad.model.Station;

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
    public int countByTrainNumber(int trainNumber) {
        return sessionFactory.getCurrentSession().createQuery("SELECT s.id FROM Train AS t INNER JOIN StationTrain AS st ON t.id = st.train_id INNER JOIN Station AS s ON st.station_id = s.id WHERE t.number = :trainNumber", Number.class).setParameter("trainNumber", trainNumber).list().size();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Integer> getIdByTrainNumberList(int trainNumber, int page, int onPage) {
        return sessionFactory.getCurrentSession().createQuery("SELECT s.id FROM Train AS t INNER JOIN StationTrain AS st ON t.id = st.train_id INNER JOIN Station AS s ON st.station_id = s.id WHERE t.number = :trainNumber ORDER BY st.time").setParameter("trainNumber", trainNumber).setFirstResult(onPage * (page - 1)).setMaxResults(onPage).list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public int getIdByStationNameSingle(String stationName) {
        return sessionFactory.getCurrentSession().createQuery("SELECT s.id FROM Station AS s WHERE s.station_name = :stationName", Number.class).setParameter("stationName", stationName).getSingleResult().intValue();
    }

    @Override
    @SuppressWarnings("unchecked")
    public String getStationNameByIdSingle(int id) {
        return sessionFactory.getCurrentSession().createQuery("SELECT s.station_name FROM Station AS s WHERE s.id = :id").setParameter("id", id).getSingleResult().toString();
    }

    @Override
    @SuppressWarnings("unchecked")
    public int countByStationName(String stationName) {
        return sessionFactory.getCurrentSession().createQuery("SELECT s.id FROM Station AS s WHERE s.station_name = :stationName", Number.class).setParameter("stationName", stationName).list().size();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void add(Station newStation) {
        sessionFactory.getCurrentSession().save(newStation);
    }

}