package railroad.dao.impl;

import org.springframework.stereotype.Repository;
import railroad.dao.TrainDAO;
import railroad.model.Train;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class TrainDAOImpl implements TrainDAO {
    private static final AtomicInteger AUTO_ID = new AtomicInteger(0);
    private static Map<Integer, Train> trains = new HashMap<>();

    static {
        Train train1 = new Train();
        train1.setId(AUTO_ID.getAndIncrement());
        train1.setNumber(123456);
        trains.put(train1.getId(), train1);
    }

    @Override
    public List<Train> allTrains() {
        return new ArrayList<>(trains.values());
    }

    @Override
    public void add(Train train) {
        train.setId(AUTO_ID.getAndIncrement());
        trains.put(train.getId(), train);
    }

    @Override
    public void delete(Train train) {
        trains.remove(train.getId());
    }

    @Override
    public void edit(Train train) {
        trains.put(train.getId(), train);
    }

    @Override
    public Train getById(int id) {
        return trains.get(id);
    }
}