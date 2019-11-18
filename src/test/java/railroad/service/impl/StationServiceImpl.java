package railroad.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import railroad.dao.StationDAO;
import railroad.model.Station;
import railroad.model.additional.StationTime;
import railroad.service.StationService;

import java.util.List;

@Service
public class StationServiceImpl implements StationService {
    private StationDAO stationDAO;

    @Autowired
    public void setStationDAO(StationDAO stationDAO) {
        this.stationDAO = stationDAO;
    }

    @Override
    @Transactional
    public int stationsByTrainCount(int trainNumber) {
        return stationDAO.stationsByTrainCount(trainNumber);
    }

    @Override
    @Transactional
    public List<StationTime> stationsByTrain(int trainNumber, int page) {
        return stationDAO.stationsByTrain(trainNumber, page);
    }

    @Override
    @Transactional
    public boolean isExist(String stationName) {
        return stationDAO.isExist(stationName);
    }

    @Override
    @Transactional
    public void add(String stationName) {
        stationDAO.add(stationName);
    }

    @Override
    @Transactional
    public boolean isExistForTrain(int trainNumber, String stationName, String stopTime) {
        return stationDAO.isExistForTrain(trainNumber, stationName, stopTime);
    }

}