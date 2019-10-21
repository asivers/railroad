package railroad.service.impl;

import railroad.dao.TrainDAO;
import railroad.dao.impl.TrainDAOImpl;
import railroad.model.Train;
import railroad.service.TrainService;

import java.util.List;

public class TrainServiceImpl implements TrainService {
    private TrainDAO trainDAO = new TrainDAOImpl();

    @Override
    public List<Train> allTrains() {
        return trainDAO.allTrains();
    }

    @Override
    public void add(Train train) {
        trainDAO.add(train);
    }

    @Override
    public void delete(Train train) {
        trainDAO.delete(train);
    }

    @Override
    public void edit(Train train) {
        trainDAO.edit(train);
    }

    @Override
    public Train getById(int id) {
        return trainDAO.getById(id);
    }
}