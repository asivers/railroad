package railroad.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import railroad.dao.StationDAO;
import railroad.model.Station;
import railroad.model.additional.StationTime;
import railroad.service.StationService;
import java.util.List;

@Service
public class StationServiceImpl implements StationService {
    private StationDAO stationDAO;

    @Autowired
    public void setStationDAO(StationDAO stationDAO) { this.stationDAO = stationDAO; }

    @Override
    @Transactional
    public List<Station> allStations() { return stationDAO.allStations(); }

    @Override
    @Transactional
    public List<StationTime> stationsByTrain(int trainNumber) { return stationDAO.stationsByTrain(trainNumber); }

    @Override
    @Transactional
    public void add(Station station) { stationDAO.add(station); }

    @Override
    @Transactional
    public Station getById(int id) { return stationDAO.getById(id); }
}