package railroad.service;

import railroad.model.Passenger;
import java.util.List;

public interface PassengerService {
    List<Passenger> allPassengers();
    void add(Passenger passenger);
    void delete(Passenger passenger);
    void edit(Passenger passenger);
    Passenger getById(int id);
}