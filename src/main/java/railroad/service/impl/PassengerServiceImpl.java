package railroad.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public List<Passenger> allPassengers() {
        return passengerDAO.allPassengers();
    }

    @Override
    @Transactional
    public boolean isOnTrain(String firstName, String secondName, String birthDate, int trainNumber) { return passengerDAO.isOnTrain(firstName, secondName, birthDate, trainNumber); }

    @Override
    @Transactional
    public void add(Passenger passenger) {
        passengerDAO.add(passenger);
    }

    @Override
    @Transactional
    public Passenger getById(int id) {
        return passengerDAO.getById(id);
    }
}