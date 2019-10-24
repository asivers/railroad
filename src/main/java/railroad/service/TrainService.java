package railroad.service;

import railroad.model.Train;
import railroad.model.additional.TrainTime;
import railroad.model.additional.TrainTimeTime;

import java.sql.Time;
import java.util.List;

public interface TrainService {
    List<Train> allTrains();
    List<TrainTime> trainsByStation(String stationName);
    List<TrainTimeTime> trainsBySearch(String departureStationName, String arrivalStationName, Time lowerTime, Time upperTime);
    void add(Train train);
    Train getById(int id);
}