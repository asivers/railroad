package railroad.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import railroad.dao.StationDAO;
import railroad.dao.StationTrainDAO;
import railroad.dao.TrainDAO;
import railroad.model.Station;
import railroad.model.StationTrain;
import railroad.model.additional.StationTime;
import railroad.service.StationService;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static railroad.service.impl.TimeSupport.TimeToLong;

@Service
public class StationServiceImpl implements StationService {

    private StationDAO stationDAO;
    @Autowired
    public void setStationDAO(StationDAO stationDAO) {
        this.stationDAO = stationDAO;
    }

    private StationTrainDAO stationTrainDAO;
    @Autowired
    public void setStationTrainDAO(StationTrainDAO stationTrainDAO) {
        this.stationTrainDAO = stationTrainDAO;
    }

    private TrainDAO trainDAO;
    @Autowired
    public void setTrainDAO(TrainDAO trainDAO) {
        this.trainDAO = trainDAO;
    }

    /**
     * Gets number of stations by train number.
     *
     * @param trainNumber train number
     */
    @Override
    @Transactional(readOnly = true)
    public int stationsByTrainCount(int trainNumber) {
        return stationDAO.countByTrainNumber(trainNumber);
    }

    /**
     * Gets station's info list by train number.
     *
     * @param trainNumber train number
     * @param page page number
     */
    @Override
    @Transactional(readOnly = true)
    public List<StationTime> stationsByTrain(int trainNumber, int page) {
        int onPage = 8;
        List<Integer> stationIDs = stationDAO.getIdByTrainNumberList(trainNumber, page, onPage);
        List<StationTime> stationsTimes = new ArrayList<>();
        String stopTime = "";
        for (Integer id : stationIDs) {
            String stationName = stationDAO.getStationNameByIdSingle(id);
            stopTime = stationTrainDAO.getStopTimeByStationIdAndTrainNumberSingle(id, trainNumber);
            stopTime = TimeSupport.LongToTime(TimeToLong(stopTime) - 10800000);
            stationsTimes.add(new StationTime(stationName, stopTime));
        }
        return stationsTimes;
    }

    /**
     * Checks if the station already exists.
     * If not, adds it to database.
     *
     * @param stationName station name
     */
    @Override
    @Transactional
    public boolean isExist(String stationName) {
        int isNewStation = stationDAO.countByStationName(stationName);
        if (isNewStation == 0) {
            Station newStation = new Station();
            newStation.setStationName(stationName);
            stationDAO.add(newStation);
            return false;
        }
        else
            return true;
    }

    /**
     * Checks if the station already exists for train.
     * If not, adds relation between them.
     *
     * @param trainNumber train number
     * @param stationName station name
     * @param stopTime stop time
     */
    @Override
    @Transactional
    public boolean isExistForTrain(int trainNumber, String stationName, String stopTime) {
        String tzStopTime = TimeSupport.LongToTime(TimeSupport.TimeToLong(stopTime) + 10800000);
        List<Object> timesList = stationTrainDAO.getStopTimeByTrainNumberList(trainNumber);
        boolean newTime = true;
        for (Object x : timesList) {
            if (x.toString().substring(0, 5).equals(tzStopTime)) {
                newTime = false;
                break;
            }
        }
        if (!newTime)
            return true;
        else {
            int isNewStation = stationDAO.countByStationName(stationName);
            int stationID = 0;
            int trainID = trainDAO.getIdByTrainNumberSingle(trainNumber);
            if (isNewStation == 0) {
                Station newStation = new Station();
                newStation.setStationName(stationName);
                stationDAO.add(newStation);
                stationID = stationDAO.getIdByStationNameSingle(stationName);
                StationTrain newStationTrain = new StationTrain();
                newStationTrain.setStationId(stationID);
                newStationTrain.setTrainId(trainID);
                newStationTrain.setTime(new Time(TimeSupport.TimeToLong(stopTime)));
                stationTrainDAO.add(newStationTrain);
                return false;
            } else {
                stationID = stationDAO.getIdByStationNameSingle(stationName);
                int isByTrain = stationTrainDAO.countByStationIdAndTrainId(stationID, trainID);
                if (isByTrain == 0) {
                    StationTrain newStationTrain = new StationTrain();
                    newStationTrain.setStationId(stationID);
                    newStationTrain.setTrainId(trainID);
                    newStationTrain.setTime(new Time(TimeSupport.TimeToLong(stopTime)));
                    stationTrainDAO.add(newStationTrain);
                    return false;
                }
                else
                    return true;
            }
        }
    }

    /**
     * Checks if the station already exists for train.
     * If so, deletes relation between them.
     *
     * @param trainNumber train number
     * @param stationName station name
     */
    @Override
    @Transactional
    public boolean isExistForTrainDelete(int trainNumber, String stationName) {
        int isNewStation = stationDAO.countByStationName(stationName);
        int stationID = 0;
        int trainID = trainDAO.getIdByTrainNumberSingle(trainNumber);
        if (isNewStation == 0) {
            return false;
        } else {
            stationID = stationDAO.getIdByStationNameSingle(stationName);
            int isByTrain = stationTrainDAO.countByStationIdAndTrainId(stationID, trainID);
            if (isByTrain == 0)
                return false;
            else {
                stationTrainDAO.deleteRelation(stationID, trainID);
                return true;
            }
        }

    }

}