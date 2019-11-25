package railroad.service;

import railroad.model.additional.StationTime;

import java.util.List;

public interface StationService {

    /**
     * Gets number of stations by train number.
     *
     * @param trainNumber train number
     */
    int stationsByTrainCount(int trainNumber);

    /**
     * Gets station's info list by train number.
     *
     * @param trainNumber train number
     * @param page page number
     */
    List<StationTime> stationsByTrain(int trainNumber, int page);

    /**
     * Checks if the station already exists.
     * If not, adds it to database.
     *
     * @param stationName station name
     */
    boolean isExist(String stationName);

    /**
     * Checks if the station already exists for train.
     * If not, adds relation between them.
     *
     * @param trainNumber train number
     * @param stationName station name
     * @param stopTime stop time
     */
    boolean isExistForTrain(int trainNumber, String stationName, String stopTime);

    /**
     * Checks if the station already exists for train.
     * If so, deletes relation between them.
     *
     * @param trainNumber train number
     * @param stationName station name
     */
    boolean isExistForTrainDelete(int trainNumber, String stationName);

}