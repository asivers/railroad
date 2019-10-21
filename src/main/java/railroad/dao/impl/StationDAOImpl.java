package railroad.dao.impl;

import railroad.dao.StationDAO;
import railroad.model.Station;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class StationDAOImpl implements StationDAO {
    private static final AtomicInteger AUTO_ID = new AtomicInteger(0);
    private static Map<Integer, Station> stations = new HashMap<>();

    static {
        Station station1 = new Station();
        station1.setId(AUTO_ID.getAndIncrement());
        station1.setName("Dachnoe");
        stations.put(station1.getId(), station1);
    }

    @Override
    public List<Station> allStations() {
        return new ArrayList<>(stations.values());
    }

    @Override
    public void add(Station station) {
        station.setId(AUTO_ID.getAndIncrement());
        stations.put(station.getId(), station);
    }

    @Override
    public void delete(Station station) {
        stations.remove(station.getId());
    }

    @Override
    public void edit(Station station) {
        stations.put(station.getId(), station);
    }

    @Override
    public Station getById(int id) {
        return stations.get(id);
    }
}