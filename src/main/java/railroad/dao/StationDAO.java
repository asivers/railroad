package railroad.dao;

import railroad.model.Station;
import railroad.model.additional.StationTime;

import java.util.List;

public interface StationDAO {
    List<Station> allStations();
    List<StationTime> stationsByTrain(int trainNumber);
    void add(Station station);
    Station getById(int id);
}