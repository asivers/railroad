package railroad.service;

import railroad.model.Station;
import railroad.model.Train;
import railroad.model.TrainTime;

import java.util.List;

public interface TrainService {
    List<Train> allTrains();
        List<TrainTime> trainsByStation(String stationName);
    void add(Train train);
    Train getById(int id);
}