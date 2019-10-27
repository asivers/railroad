package railroad.dao;

import railroad.model.Passenger;
import railroad.model.additional.PassengerInfo;

import java.util.List;

public interface PassengerDAO {

    List<Passenger> allPassengers();

    boolean isOnTrain(String firstName, String secondName, String birthDate, int trainNumber);

    int passengersByTrainCount(int trainNumber);
    List<PassengerInfo> passengersByTrain(int trainNumber, int page);

    void add(Passenger passenger);
    Passenger getById(int id);
}