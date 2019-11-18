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
    public int allTrainsCount() {
        return trainDAO.allTrainsCount();
    }

    @Override
    @Transactional
    public List<Integer> allTrains(int page) {
        return trainDAO.allTrains(page);
    }

    @Override
    @Transactional
    public int trainsByStationCount(String stationName) { return trainDAO.trainsByStationCount(stationName); }

    @Override
    @Transactional
    public List<TrainTime> trainsByStation(String stationName, int page) { return trainDAO.trainsByStation(stationName, page); }

    @Override
    @Transactional
    public int trainsBySearchCount(String departureStationName, String arrivalStationName, Time lowerTime, Time upperTime) { return trainDAO.trainsBySearchCount(departureStationName, arrivalStationName, lowerTime, upperTime); }

    @Override
    @Transactional
    public List<TrainTimeTime> trainsBySearch(String departureStationName, String arrivalStationName, Time lowerTime, Time upperTime, int page) { return trainDAO.trainsBySearch(departureStationName, arrivalStationName, lowerTime, upperTime, page); }

    @Override
    @Transactional
    public boolean freeSeats(int trainNumber) { return trainDAO.freeSeats(trainNumber); }

    @Override
    @Transactional
    public boolean isExist(int trainNumber, int seats) { return trainDAO.isExist(trainNumber, seats); }

    @Override
    @Transactional
    public void add(int trainNumber, int seats) { trainDAO.add(trainNumber, seats); }

//    @Override
//    @Transactional
//    public String trainsForTimeBoard(String stationName) { trainDAO.trainsForTimeBoard(stationName); };

}