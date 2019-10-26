package railroad.service;

import railroad.model.Train;
import railroad.model.additional.TrainTime;
import railroad.model.additional.TrainTimeTime;

import java.sql.Time;
import java.util.List;

public interface TrainService {
    List<Train> allTrains();
    int trainsByStationCount(String stationName);
    List<TrainTime> trainsByStation(String stationName, int page);
    int trainsBySearchCount(String departureStationName, String arrivalStationName, Time lowerTime, Time upperTime);
    List<TrainTimeTime> trainsBySearch(String departureStationName, String arrivalStationName, Time lowerTime, Time upperTime, int page);
    void add(Train train);
    Train getById(int id);
}