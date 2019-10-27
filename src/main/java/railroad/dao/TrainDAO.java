package railroad.dao;

import railroad.model.Train;
import railroad.model.additional.TrainTime;
import railroad.model.additional.TrainTimeTime;

import java.sql.Time;
import java.util.List;

public interface TrainDAO {
    List<Train> allTrains();
    int trainsByStationCount(String stationName);
    List<TrainTime> trainsByStation(String stationName, int page);
    int trainsBySearchCount(String departureStationName, String arrivalStationName, Time lowerTime, Time upperTime);
    List<TrainTimeTime> trainsBySearch(String departureStationName, String arrivalStationName, Time lowerTime, Time upperTime, int page);
    boolean freeSeats(int trainNumber);
    void add(Train train);
    Train getById(int id);
}