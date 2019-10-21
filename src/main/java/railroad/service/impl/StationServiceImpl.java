package railroad.service.impl;

import railroad.dao.StationDAO;
import railroad.dao.impl.StationDAOImpl;
import railroad.model.Station;
import railroad.service.StationService;

import java.util.List;

public class StationServiceImpl implements StationService {
    private StationDAO stationDAO = new StationDAOImpl();

    @Override
    public List<Station> allStations() {
        return stationDAO.allStations();
    }

    @Override
    public void add(Station station) {
        stationDAO.add(station);
    }

    @Override
    public void delete(Station station) {
        stationDAO.delete(station);
    }

    @Override
    public void edit(Station station) {
        stationDAO.edit(station);
    }

    @Override
    public Station getById(int id) {
        return stationDAO.getById(id);
    }
}