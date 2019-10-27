package railroad.dao;

import railroad.model.additional.TrainTime;
import railroad.model.additional.TrainTimeTime;
import java.sql.Time;
import java.util.List;

public interface TrainDAO {

    int allTrainsCount();
    List<Integer> allTrains(int page);

    int trainsByStationCount(String stationName);
    List<TrainTime> trainsByStation(String stationName, int page);

    int trainsBySearchCount(String departureStationName, String arrivalStationName, Time lowerTime, Time upperTime);
    List<TrainTimeTime> trainsBySearch(String departureStationName, String arrivalStationName, Time lowerTime, Time upperTime, int page);

    boolean freeSeats(int trainNumber);

    boolean isExist(int trainNumber);
    void add(int trainNumber);

}