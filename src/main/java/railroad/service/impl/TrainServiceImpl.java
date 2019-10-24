package railroad.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import railroad.dao.TrainDAO;
import railroad.model.Train;
import railroad.model.additional.TrainTime;
import railroad.model.additional.TrainTimeTime;
import railroad.service.TrainService;

import java.sql.Time;
import java.util.List;

@Service
public class TrainServiceImpl implements TrainService {
    private TrainDAO trainDAO;

    @Autowired
    public void setTrainDAO(TrainDAO trainDAO) {
        this.trainDAO = trainDAO;
    }

    @Override
    @Transactional
    public List<Train> allTrains() {
        return trainDAO.allTrains();
    }

    @Override
    @Transactional
    public List<TrainTime> trainsByStation(String stationName) { return trainDAO.trainsByStation(stationName); }

    @Override
    @Transactional
    public List<TrainTimeTime> trainsBySearch(String departureStationName, String arrivalStationName, Time lowerTime, Time upperTime) { return trainDAO.trainsBySearch(departureStationName, arrivalStationName, lowerTime, upperTime); }

    @Override
    @Transactional
    public void add(Train train) {
        trainDAO.add(train);
    }

    @Override
    @Transactional
    public Train getById(int id) {
        return trainDAO.getById(id);
    }
}