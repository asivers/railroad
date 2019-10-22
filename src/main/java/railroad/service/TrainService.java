package railroad.service;

import railroad.model.Train;
import java.util.List;

public interface TrainService {
    List<Train> allTrains();
    void add(Train train);
    Train getById(int id);
}