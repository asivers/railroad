package railroad.dao;

import railroad.model.Station;
import java.util.List;

public interface StationDAO {
    List<Station> allStations();
    void add(Station station);
    Station getById(int id);
}