package railroad.dao;

import railroad.model.additional.PassengerInfo;
import java.util.List;

public interface PassengerDAO {

    int passengersByTrainCount(int trainNumber);
    List<PassengerInfo> passengersByTrain(int trainNumber, int page);

    boolean isOnTrain(String firstName, String secondName, String birthDate, int trainNumber);
    void add(String firstName, String secondName, String birthDate);

}