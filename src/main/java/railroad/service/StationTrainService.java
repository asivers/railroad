package railroad.service;

import railroad.model.StationTrain;
import java.util.List;

public interface StationTrainService {
    List<StationTrain> allStationTrains();
    void add(StationTrain stationTrain);
}