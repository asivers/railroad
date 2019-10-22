package railroad.service;

import railroad.model.Station;
import railroad.model.Train;
import java.util.List;

public interface TrainService {
    List<Train> allTrains();
    List<Train> trainsByStation(String stationName);
    void add(Train train);
    Train getById(int id);
}