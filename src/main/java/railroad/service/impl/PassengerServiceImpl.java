package railroad.service.impl;

import railroad.dao.PassengerDAO;
import railroad.dao.impl.PassengerDAOImpl;
import railroad.model.Passenger;
import railroad.service.PassengerService;

import java.util.List;

public class PassengerServiceImpl implements PassengerService {
    private PassengerDAO passengerDAO = new PassengerDAOImpl();

    @Override
    public List<Passenger> allPassengers() {
        return passengerDAO.allPassengers();
    }

    @Override
    public void add(Passenger passenger) {
        passengerDAO.add(passenger);
    }

    @Override
    public void delete(Passenger passenger) {
        passengerDAO.delete(passenger);
    }

    @Override
    public void edit(Passenger passenger) {
        passengerDAO.edit(passenger);
    }

    @Override
    public Passenger getById(int id) {
        return passengerDAO.getById(id);
    }
}