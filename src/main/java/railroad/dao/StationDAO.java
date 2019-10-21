package railroad.dao;

import railroad.model.Station;
import java.util.List;

public interface StationDAO {
    List<Station> allStations();
    void add(Station station);
    void delete(Station station);
    void edit(Station station);
    Station getById(int id);
}