package railroad.dao;

import railroad.model.Station;
import railroad.model.Train;
import railroad.model.TrainTime;

import java.util.List;

public interface TrainDAO {
    List<Train> allTrains();
//    List<TrainTime> trainsByStation(String stationName);
    List<Integer> trainsByStation(String stationName);
    void add(Train train);
    Train getById(int id);
}