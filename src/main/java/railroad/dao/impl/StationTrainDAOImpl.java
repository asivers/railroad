package railroad.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import railroad.dao.StationTrainDAO;
import railroad.model.StationTrain;

import java.util.List;

@Repository
@Transactional
public class StationTrainDAOImpl implements StationTrainDAO {

    private SessionFactory sessionFactory;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Gets number of relations by station's id and train's id.
     * If relation exists, the result will be 1.
     *
     * @param station_id station's id
     * @param train_id train's id
     */
    @Override
    @SuppressWarnings("unchecked")
    public int countByStationIdAndTrainId(int station_id, int train_id) {
        return sessionFactory.getCurrentSession().createQuery("SELECT st.station_id FROM StationTrain AS st WHERE st.station_id = :stationID AND st.train_id = :trainID").setParameter("stationID", station_id).setParameter("trainID", train_id).list().size();
    }

    /**
     * Gets stop time by train number.
     *
     * @param trainNumber train number
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Object> getStopTimeByTrainNumberList(int trainNumber) {
        return sessionFactory.getCurrentSession().createQuery("SELECT st.time FROM StationTrain AS st INNER JOIN Train AS t ON st.train_id = t.id WHERE t.number = :trainNumber").setParameter("trainNumber", trainNumber).list();
    }

    /**
     * Gets stop time by station's id and train number.
     *
     * @param id station's id
     * @param trainNumber train number
     */
    @Override
    @SuppressWarnings("unchecked")
    public String getStopTimeByStationIdAndTrainNumberSingle(int id, int trainNumber) {
        return sessionFactory.getCurrentSession().createQuery("SELECT st.time FROM Train AS t INNER JOIN StationTrain AS st ON t.id = st.train_id INNER JOIN Station AS s ON st.station_id = s.id WHERE s.id = :id AND t.number = :trainNumber").setParameter("id", id).setParameter("trainNumber", trainNumber).getSingleResult().toString();
    }

    /**
     * Gets stop time by train's id and station name.
     *
     * @param id train's id
     * @param stationName station name
     */
    @Override
    @SuppressWarnings("unchecked")
    public String getStopTimeByTrainIdAndStationNameSingle(int id, String stationName) {
        return sessionFactory.getCurrentSession().createQuery("SELECT st.time FROM Train AS t INNER JOIN StationTrain AS st ON t.id = st.train_id INNER JOIN Station AS s ON st.station_id = s.id WHERE t.id = :id AND s.station_name = :stationName").setParameter("id", id).setParameter("stationName", stationName).getSingleResult().toString();
    }

    /**
     * Delete relation by station's id and train's id.
     *
     * @param station_id station's id
     * @param train_id train's id
     */
    @Override
    @SuppressWarnings("unchecked")
    public void deleteRelation(int station_id, int train_id) {
        sessionFactory.getCurrentSession().createQuery("DELETE FROM StationTrain AS st WHERE st.station_id = :station_id AND st.train_id = :train_id").setParameter("station_id", station_id).setParameter("train_id", train_id).executeUpdate();
    }

    /**
     * Adds station-train relation to database.
     *
     * @param newStationTrain new station-train relation
     */
    @Override
    @SuppressWarnings("unchecked")
    public void add(StationTrain newStationTrain) {
        sessionFactory.getCurrentSession().save(newStationTrain);
    }

}