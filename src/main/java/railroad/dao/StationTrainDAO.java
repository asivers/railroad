package railroad.dao;

import railroad.model.StationTrain;

import java.util.List;

public interface StationTrainDAO {

    /**
     * Gets number of relations by station's id and train's id.
     * If relation exists, the result will be 1.
     *
     * @param station_id station's id
     * @param train_id train's id
     */
    int countByStationIdAndTrainId(int station_id, int train_id);

    /**
     * Gets stop time by train number.
     *
     * @param trainNumber train number
     */
    List<Object> getStopTimeByTrainNumberList(int trainNumber);

    /**
     * Gets stop time by station's id and train number.
     *
     * @param id station's id
     * @param trainNumber train number
     */
    String getStopTimeByStationIdAndTrainNumberSingle(int id, int trainNumber);

    /**
     * Gets stop time by train's id and station name.
     *
     * @param id train's id
     * @param stationName station name
     */
    String getStopTimeByTrainIdAndStationNameSingle(int id, String stationName);

    /**
     * Delete relation by station's id and train's id.
     *
     * @param station_id station's id
     * @param train_id train's id
     */
    void deleteRelation(int station_id, int train_id);

    /**
     * Adds station-train relation to database.
     *
     * @param newStationTrain new station-train relation
     */
    void add(StationTrain newStationTrain);
}