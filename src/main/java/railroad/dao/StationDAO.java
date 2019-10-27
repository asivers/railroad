package railroad.dao;

import railroad.model.Station;
import railroad.model.additional.StationTime;

import java.util.List;

public interface StationDAO {

    List<Station> allStations();

    int stationsByTrainCount(int trainNumber);
    List<StationTime> stationsByTrain(int trainNumber, int page);

    void add(Station station);
    Station getById(int id);
}