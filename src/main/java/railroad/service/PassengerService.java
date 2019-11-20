package railroad.service;

import railroad.model.additional.PassengerInfo;
import java.util.List;

public interface PassengerService {
    int passengersByTrainCount(int trainNumber);
    List<PassengerInfo> passengersByTrain(int trainNumber, int page);
    boolean isOnTrain(String firstName, String secondName, String birthDate, int trainNumber, int currentUserID);
}