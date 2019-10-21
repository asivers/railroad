package railroad.dao.impl;

import railroad.dao.PassengerDAO;
import railroad.model.Passenger;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class PassengerDAOImpl implements PassengerDAO {
    private static final AtomicInteger AUTO_ID = new AtomicInteger(0);
    private static Map<Integer, Passenger> passengers = new HashMap<>();

    static {
        Passenger passenger1 = new Passenger();
        passenger1.setId(AUTO_ID.getAndIncrement());
        passenger1.setName("Dachnoe");
        passengers.put(passenger1.getId(), passenger1);
    }

    @Override
    public List<Passenger> allPassengers() {
        return new ArrayList<>(passengers.values());
    }

    @Override
    public void add(Passenger passenger) {
        passenger.setId(AUTO_ID.getAndIncrement());
        passengers.put(passenger.getId(), passenger);
    }

    @Override
    public void delete(Passenger passenger) {
        passengers.remove(passenger.getId());
    }

    @Override
    public void edit(Passenger passenger) {
        passengers.put(passenger.getId(), passenger);
    }

    @Override
    public Passenger getById(int id) {
        return passengers.get(id);
    }
}