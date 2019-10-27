package railroad.service;

import railroad.model.Station;
import railroad.model.additional.StationTime;

import java.util.List;

public interface StationService {

    int stationsByTrainCount(int trainNumber);
    List<StationTime> stationsByTrain(int trainNumber, int page);

    boolean isExist(String stationName);
    void add(String stationName);

}