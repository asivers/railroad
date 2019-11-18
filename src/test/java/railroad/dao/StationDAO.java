package railroad.dao;

import railroad.model.additional.StationTime;
import java.util.List;

public interface StationDAO {

    int stationsByTrainCount(int trainNumber);
    List<StationTime> stationsByTrain(int trainNumber, int page);

    boolean isExist(String stationName);
    void add(String stationName);

    boolean isExistForTrain(int trainNumber, String stationName, String stopTime);

}