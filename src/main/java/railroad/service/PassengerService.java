package railroad.service;

import railroad.model.Passenger;
import java.util.List;

public interface PassengerService {
    List<Passenger> allPassengers();
    void add(Passenger passenger);
    Passenger getById(int id);
}