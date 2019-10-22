package railroad.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import railroad.dao.PassengerDAO;
import railroad.model.Passenger;
import railroad.service.PassengerService;
import java.util.List;

@Service
public class PassengerServiceImpl implements PassengerService {
    private PassengerDAO passengerDAO;

    @Autowired
    public void setPassengerDAO(PassengerDAO passengerDAO) {
        this.passengerDAO = passengerDAO;
    }

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