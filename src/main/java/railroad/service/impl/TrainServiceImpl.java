package railroad.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import railroad.dao.TrainDAO;
import railroad.model.Train;
import railroad.service.TrainService;
import java.util.List;

@Service
public class TrainServiceImpl implements TrainService {
    private TrainDAO trainDAO;

    @Autowired
    public void setTrainDAO(TrainDAO trainDAO) {
        this.trainDAO = trainDAO;
    }

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