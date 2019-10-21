package railroad.service;

import railroad.model.Station;
import java.util.List;

public interface StationService {
    List<Station> allStations();
    void add(Station station);
    void delete(Station station);
    void edit(Station station);
    Station getById(int id);
}