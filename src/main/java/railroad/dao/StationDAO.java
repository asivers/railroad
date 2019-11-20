package railroad.dao;

import railroad.model.Station;
import railroad.model.additional.StationTime;
import java.util.List;

public interface StationDAO {
    int countByTrainNumber(int trainNumber);
    List<Integer> getIdByTrainNumberList(int trainNumber, int page, int onPage);
    int getIdByStationNameSingle(String stationName);
    String getStationNameByIdSingle(int id);
    int countByStationName(String stationName);
    void add(Station newStation);
}