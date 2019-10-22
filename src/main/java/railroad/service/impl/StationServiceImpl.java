package railroad.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import railroad.dao.StationDAO;
import railroad.model.Station;
import railroad.service.StationService;
import java.util.List;

@Service
public class StationServiceImpl implements StationService {
    private StationDAO stationDAO;

    @Autowired
    public void setStationDAO(StationDAO stationDAO) {
        this.stationDAO = stationDAO;
    }

    @Override
    public List<Station> allStations() {
        return stationDAO.allStations();
    }

    @Override
    public void add(Station station) {
        stationDAO.add(station);
    }

    @Override
    public Station getById(int id) {
        return stationDAO.getById(id);
    }
}