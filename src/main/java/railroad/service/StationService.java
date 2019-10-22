package railroad.service;

import railroad.model.Station;
import java.util.List;

public interface StationService {
    List<Station> allStations();
    void add(Station station);
    Station getById(int id);
}