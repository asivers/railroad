package railroad.dao;

import railroad.model.Train;
import railroad.model.additional.TrainTime;
import railroad.model.additional.TrainTimeTime;
import java.sql.Time;
import java.util.List;

public interface TrainDAO {

    /**
     * Gets number of all trains in the database.
     */
    int countAllTrains();

    /**
     * Gets list of all trains in the database.
     * @param page page number
     * @param onPage number of trains on the page
     */
    List<Integer> getTrainNumberList(int page, int onPage);

    /**
     * Gets number of trains by station name.
     *
     * @param stationName station name
     */
    int countByStationName(String stationName);

    /**
     * Gets train numbers list by station name.
     *
     * @param stationName station name
     * @param page page number
     * @param onPage number of trains on the page
     */
    List<Integer> getIdByStationNameList(String stationName, int page, int onPage);

    /**
     * Gets train number by id.
     *
     * @param id train's id
     */
    int getTrainNumberByIdSingle(int id);

    /**
     * Gets number of trains which go to arrival station and arrive
     * not earlier than lower time but not later than upper time.
     *
     * @param arrivalStationName arrival station
     * @param lowerTime lower time
     * @param upperTime upper time
     */
    List<Integer> getIdByArrivalSearchList(String arrivalStationName, Time lowerTime, Time upperTime);

    /**
     * Gets number of trains which go to departure station and arrive
     * there not later than they arrive to arrival station.
     * If the train is appropriate, the result will be 1.
     *
     * @param train_id train's id
     * @param departureStationName departure station
     * @param untilTime time until which the train must arrive to arrival station.
     */
    int isAppropriateBySearch(int train_id, String departureStationName, Time untilTime);

    /**
     * Gets train's id list for trains which go from departure station to arrival
     * station and arrive not earlier than lower time but not later than upper time.
     *
     * @param departureStationName departure station
     * @param arrivalStationName arrival station
     * @param lowerTime lower time
     * @param upperTime upper time
     * @param page page number
     * @param onPage number of trains on the page
     */
    List<Integer> getIdBySearchList(String departureStationName, String arrivalStationName, Time lowerTime, Time upperTime, int page, int onPage);

    /**
     * Gets number of seats by train's id.
     *
     * @param id train's id
     */
    int getSeatsByIdSingle(int id);

    /**
     * Gets number of trains by its id.
     * If train exists, the result will be 1.
     *
     * @param trainNumber train number
     */
    int countByTrainNumber(int trainNumber);

    /**
     * Gets train's id by its number.
     *
     * @param trainNumber train number
     */
    int getIdByTrainNumberSingle(int trainNumber);

    /**
     * Gets train number by ticket's id.
     *
     * @param id ticket's id
     */
    int getTrainNumberByTicketIdSingle(int id);

    /**
     * Deletes train by its number.
     *
     * @param trainNumber train number
     */
    void deleteByTrainNumber(int trainNumber);

    /**
     * Adds new train to database.
     *
     * @param newTrain new train
     */
    void add(Train newTrain);
}