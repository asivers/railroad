package railroad.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import railroad.dao.PassengerDAO;
import railroad.model.Passenger;
import railroad.model.additional.PassengerInfo;
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
    public int passengersByTrainCount(int trainNumber) { return passengerDAO.passengersByTrainCount(trainNumber); }

    @Override
    @Transactional
    public List<PassengerInfo> passengersByTrain(int trainNumber, int page) { return passengerDAO.passengersByTrain(trainNumber, page); }

    @Override
    @Transactional
    public boolean isOnTrain(String firstName, String secondName, String birthDate, int trainNumber) { return passengerDAO.isOnTrain(firstName, secondName, birthDate, trainNumber); }

    @Override
    @Transactional
    public void add(String firstName, String secondName, String birthDate) {
        passengerDAO.add(firstName, secondName, birthDate);
    }

}