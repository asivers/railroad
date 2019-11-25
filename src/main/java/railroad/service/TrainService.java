package railroad.service;

import railroad.model.additional.TrainTime;
import railroad.model.additional.TrainTimeTime;
import java.util.List;

public interface TrainService {

    /**
     * Gets number of all trains in the database.
     */
    int allTrainsCount();

    /**
     * Gets list of all trains in the database.
     * @param page page number
     */
    List<Integer> allTrains(int page);

    /**
     * Gets number of trains by station name.
     *
     * @param stationName station name
     */
    int trainsByStationCount(String stationName);

    /**
     * Gets train's info list by station name.
     *
     * @param stationName station name
     * @param page page number
     */
    List<TrainTime> trainsByStation(String stationName, int page);

    /**
     * Gets number of trains which go from departure station to arrival station
     * and arrive not earlier than lower time but not later than upper time.
     *
     * @param departureStationName departure station
     * @param arrivalStationName arrival station
     * @param lowerTimeString lower time
     * @param upperTimeString upper time
     */
    int trainsBySearchCount(String departureStationName, String arrivalStationName, String lowerTimeString, String upperTimeString);

    /**
     * Gets train's info list for trains which go from departure station to arrival
     * station and arrive not earlier than lower time but not later than upper time.
     *
     * @param departureStationName departure station
     * @param arrivalStationName arrival station
     * @param lowerTimeString lower time
     * @param upperTimeString upper time
     * @param page page number
     */
    List<TrainTimeTime> trainsBySearch(String departureStationName, String arrivalStationName, String lowerTimeString, String upperTimeString, int page);

    /**
     * Checks if there are free seats on the train.
     *
     * @param trainNumber train number
     */
    boolean freeSeats(int trainNumber);

    /**
     * Checks if the train already exists.
     * If not, adds it to database.
     *
     * @param trainNumber train number
     * @return true if there are free seats, false otherwise
     */
    boolean isExist(int trainNumber, int seats);

    /**
     * Deletes train by its number.
     *
     * @param trainNumber train number
     * @return list of stations to send notification to timeboard
     */
    List<String> deleteTrain(int trainNumber);

    /**
     * Uploads list of trains and stop times by station name.
     * Transforms data into string.
     * Sends notification to timeboard through messageSender.
     *
     * @param stationName station name
     * @param page page number
     */
    void trainsByStationTB(String stationName, int page);

}