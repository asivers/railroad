package railroad.dao;

import railroad.model.Train;
import java.util.List;

public interface TrainDAO {
    List<Train> allTrains();
    void add(Train train);
    void delete(Train train);
    void edit(Train train);
    Train getById(int id);
}