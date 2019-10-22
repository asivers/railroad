package railroad.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import railroad.dao.TrainDAO;
import railroad.model.Station;
import railroad.model.Train;
import railroad.model.TrainTime;
import railroad.service.TrainService;
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
//    public List<TrainTime> trainsByStation(String stationName) {
//        return trainDAO.trainsByStation(stationName);
//    }
    public List<Integer> trainsByStation(String stationName) {
        return trainDAO.trainsByStation(stationName);
    }

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