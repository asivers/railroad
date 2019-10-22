package railroad.dao;

import railroad.model.StationTrain;
import java.util.List;

public interface StationTrainDAO {
    List<StationTrain> allStationTrains();
    void add(StationTrain stationTrain);
}