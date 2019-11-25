package railroad.service;

import railroad.model.additional.PassengerInfo;
import java.util.List;

public interface PassengerService {

    /**
     * Gets number of passengers by train number
     *
     * @param trainNumber train number
     */
    int passengersByTrainCount(int trainNumber);

    /**
     * Gets passenger's info list by train number
     *
     * @param trainNumber train number
     * @param page page number
     */
    List<PassengerInfo> passengersByTrain(int trainNumber, int page);

    /**
     * Checks if the passenger already on the train.
     * If not, adds relation between them (ticket).
     *
     * @param firstName passenger first name
     * @param secondName passenger second name
     * @param birthDate passenger birth name
     * @param trainNumber train number
     * @param currentUserID current user's id
     */
    boolean isOnTrain(String firstName, String secondName, String birthDate, int trainNumber, int currentUserID);

}