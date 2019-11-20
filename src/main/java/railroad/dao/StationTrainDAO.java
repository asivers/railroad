package railroad.dao;

import railroad.model.StationTrain;

import java.util.List;

public interface StationTrainDAO {
    int countByStationIdAndTrainId(int station_id, int train_id);
    List<Object> getTimeByTrainNumberList(int trainNumber);
    String getStopTimeByStationIdAndTrainNumberSingle(int id, int trainNumber);
    String getStopTimeByTrainIdAndStationNameSingle(int id, String stationName);
    void add(StationTrain newStationTrain);
}