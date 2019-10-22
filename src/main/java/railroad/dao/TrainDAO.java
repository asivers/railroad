package railroad.dao;

import railroad.model.Train;
import java.util.List;

public interface TrainDAO {
    List<Train> allTrains();
    void add(Train train);
    Train getById(int id);
}