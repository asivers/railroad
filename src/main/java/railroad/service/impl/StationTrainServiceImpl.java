package railroad.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import railroad.dao.StationTrainDAO;
import railroad.model.StationTrain;
import railroad.service.StationTrainService;
import java.util.List;

@Service
public class StationTrainServiceImpl implements StationTrainService {
    private StationTrainDAO stationTrainDAO;

    @Autowired
    public void setStationTrainDAO(StationTrainDAO stationTrainDAO) {
        this.stationTrainDAO = stationTrainDAO;
    }

    @Override
    public List<StationTrain> allStationTrains() {
        return stationTrainDAO.allStationTrains();
    }

    @Override
    public void add(StationTrain stationTrain) {
        stationTrainDAO.add(stationTrain);
    }

}