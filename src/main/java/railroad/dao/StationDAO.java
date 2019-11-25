package railroad.dao;

import railroad.model.Station;
import railroad.model.additional.StationTime;
import java.util.List;

public interface StationDAO {

    /**
     * Gets number of stations by train number.
     *
     * @param trainNumber train number
     */
    int countByTrainNumber(int trainNumber);

    /**
     * Gets station's id list by train number.
     *
     * @param trainNumber train number
     * @param page page number
     * @param onPage number of passengers on the page
     */
    List<Integer> getIdByTrainNumberList(int trainNumber, int page, int onPage);

    /**
     * Gets station names list by train number.
     *
     * @param trainNumber train number
     * @param page page number
     * @param onPage number of passengers on the page
     */
    List<String> getStationNameByTrainNumberList(int trainNumber, int page, int onPage);

    /**
     * Gets station's id by its name.
     *
     * @param stationName station name
     */
    int getIdByStationNameSingle(String stationName);

    /**
     * Gets station name by id.
     *
     * @param id station's id
     */
    String getStationNameByIdSingle(int id);

    /**
     * Gets number of stations by its id.
     * If station exists, the result will be 1.
     *
     * @param stationName station name
     */
    int countByStationName(String stationName);

    /**
     * Adds new station to database.
     *
     * @param newStation new station
     */
    void add(Station newStation);
}