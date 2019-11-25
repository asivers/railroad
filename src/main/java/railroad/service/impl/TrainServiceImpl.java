package railroad.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import railroad.dao.StationDAO;
import railroad.dao.StationTrainDAO;
import railroad.dao.TicketDAO;
import railroad.dao.TrainDAO;
import railroad.messaging.MessageSender;
import railroad.model.Train;
import railroad.model.additional.TrainTime;
import railroad.model.additional.TrainTimeTime;
import railroad.service.TrainService;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static railroad.service.impl.TimeSupport.TimeToLong;

@Service
public class TrainServiceImpl implements TrainService {

    @Autowired
    MessageSender messageSender;

    private TrainDAO trainDAO;
    @Autowired
    public void setTrainDAO(TrainDAO trainDAO) {
        this.trainDAO = trainDAO;
    }

    private StationTrainDAO stationTrainDAO;
    @Autowired
    public void setStationTrainDAO(StationTrainDAO stationTrainDAO) {
        this.stationTrainDAO = stationTrainDAO;
    }

    private TicketDAO ticketDAO;
    @Autowired
    public void setTicketDAO(TicketDAO ticketDAO) { this.ticketDAO = ticketDAO; }

    private StationDAO stationDAO;
    @Autowired
    public void setStationDAO(StationDAO stationDAO) {
        this.stationDAO = stationDAO;
    }

    /**
     * Gets number of all trains in the database.
     */
    @Override
    @Transactional(readOnly = true)
    public int allTrainsCount() {
        return trainDAO.countAllTrains();
    }

    /**
     * Gets list of all trains in the database.
     * @param page page number
     */
    @Override
    @Transactional(readOnly = true)
    public List<Integer> allTrains(int page) {
        int onPage = 7;
        return trainDAO.getTrainNumberList(page, onPage);
    }

    /**
     * Gets number of trains by station name.
     *
     * @param stationName station name
     */
    @Override
    @Transactional(readOnly = true)
    public int trainsByStationCount(String stationName) {
        return trainDAO.countByStationName(stationName);
    }

    /**
     * Gets train's info list by station name.
     *
     * @param stationName station name
     * @param page page number
     */
    @Override
    @Transactional(readOnly = true)
    public List<TrainTime> trainsByStation(String stationName, int page) {
        int onPage = 7;
        List<Integer> trainIDs = trainDAO.getIdByStationNameList(stationName, page, onPage);
        List<TrainTime> trainsTimes = new ArrayList<>();
        String stopTime = "";
        for (Integer id : trainIDs) {
            int trainNumber = trainDAO.getTrainNumberByIdSingle(id);
            stopTime = stationTrainDAO.getStopTimeByTrainIdAndStationNameSingle(id, stationName);
            stopTime = TimeSupport.LongToTime(TimeToLong(stopTime) - 10800000);
            trainsTimes.add(new TrainTime(trainNumber, stopTime));
        }
        return trainsTimes;
    }

    /**
     * Gets number of trains which go from departure station to arrival station
     * and arrive not earlier than lower time but not later than upper time.
     *
     * @param departureStationName departure station
     * @param arrivalStationName arrival station
     * @param lowerTimeString lower time
     * @param upperTimeString upper time
     */
    @Override
    @Transactional(readOnly = true)
    public int trainsBySearchCount(String departureStationName, String arrivalStationName, String lowerTimeString, String upperTimeString) {
        Time lowerTime = new Time((Integer.parseInt(lowerTimeString.substring(0, 2)) * 60 + Integer.parseInt(lowerTimeString.substring(3))) * 60000);
        Time upperTime = new Time((Integer.parseInt(upperTimeString.substring(0, 2)) * 60 + Integer.parseInt(upperTimeString.substring(3))) * 60000);
        List<Integer> idByArrival = trainDAO.getIdByArrivalSearchList(arrivalStationName, lowerTime, upperTime);
        int countBySearch = 0;
        for (int id : idByArrival) {
            String untilTimeString = stationTrainDAO.getStopTimeByTrainIdAndStationNameSingle(id, arrivalStationName);
            Time untilTime = new Time((Integer.parseInt(untilTimeString.substring(0, 2)) * 60 + Integer.parseInt(untilTimeString.substring(3, 5))) * 60000 - 10800000);
            if (trainDAO.isAppropriateBySearch(id, departureStationName, untilTime) > 0)
                countBySearch++;
        }
        return countBySearch;
    }

    /**
     * Gets train's info list for trains which go from departure station to arrival
     * station and arrive not earlier than lower time but not later than upper time.
     *
     * @param departureStationName departure station
     * @param arrivalStationName arrival station
     * @param lowerTimeString lower time
     * @param upperTimeString upper time
     * @param page page number
     */
    @Override
    @Transactional(readOnly = true)
    public List<TrainTimeTime> trainsBySearch(String departureStationName, String arrivalStationName, String lowerTimeString, String upperTimeString, int page) {
        int onPage = 7;
        Time lowerTime = new Time((Integer.parseInt(lowerTimeString.substring(0, 2)) * 60 + Integer.parseInt(lowerTimeString.substring(3))) * 60000);
        Time upperTime = new Time((Integer.parseInt(upperTimeString.substring(0, 2)) * 60 + Integer.parseInt(upperTimeString.substring(3))) * 60000);
        List<Integer> trainIDs = trainDAO.getIdBySearchList(departureStationName, arrivalStationName, lowerTime, upperTime, page, onPage);
        List<TrainTimeTime> trainsBothTimes = new ArrayList<>();
        String trueDepartureTime = "";
        String trueArrivalTime = "";
        for (Integer id : trainIDs) {
            int tmpNumber = trainDAO.getTrainNumberByIdSingle(id);
            trueDepartureTime = stationTrainDAO.getStopTimeByTrainIdAndStationNameSingle(id, departureStationName);
            trueArrivalTime = stationTrainDAO.getStopTimeByTrainIdAndStationNameSingle(id, arrivalStationName);
            if (TimeSupport.TimeToLong(trueArrivalTime) - 10800000 > TimeSupport.TimeToLong(trueDepartureTime) - 10800000)
                trainsBothTimes.add(new TrainTimeTime(tmpNumber, TimeSupport.LongToTime(TimeToLong(trueDepartureTime) - 10800000), TimeSupport.LongToTime(TimeToLong(trueArrivalTime) - 10800000)));
        }
        return trainsBothTimes;
    }

    /**
     * Checks if there are free seats on the train.
     *
     * @param trainNumber train number
     * @return true if there are free seats, false otherwise
     */
    @Override
    @Transactional(readOnly = true)
    public boolean freeSeats(int trainNumber) {
        int trainID = trainDAO.getIdByTrainNumberSingle(trainNumber);
        int tickets = ticketDAO.countByTrainId(trainID);
        int seats = trainDAO.getSeatsByIdSingle(trainID);
        if (seats > tickets)
            return true;
        else
            return false;
    }

    /**
     * Checks if the train already exists.
     * If not, adds it to database.
     *
     * @param trainNumber train number
     */
    @Override
    @Transactional
    public boolean isExist(int trainNumber, int seats) {
        int isNewTrain = trainDAO.countByTrainNumber(trainNumber);
        if (isNewTrain == 0) {
            Train newTrain = new Train();
            newTrain.setNumber(trainNumber);
            newTrain.setSeats(seats);
            trainDAO.add(newTrain);
            return false;
        }
        else
            return true;
    }

    /**
     * Deletes train by its number.
     *
     * @param trainNumber train number
     * @return list of stations to send notification to timeboard
     */
    @Override
    @Transactional
    public List<String> deleteTrain(int trainNumber) {
        int stationsCount = stationDAO.countByTrainNumber(trainNumber);
        List<String> stations = stationDAO.getStationNameByTrainNumberList(trainNumber, 1, stationsCount);
        trainDAO.deleteByTrainNumber(trainNumber);
        return stations;
    }

    /**
     * Uploads list of trains and stop times by station name.
     * Transforms data into string.
     * Sends notification to timeboard through messageSender.
     *
     * @param stationName station name
     * @param page page number
     */
    @Override
    @Transactional(readOnly = true)
    public void trainsByStationTB(String stationName, int page) {
        int onPage = 7;
        int trainsCount = trainDAO.countByStationName(stationName);
        int pagesCount = (trainsCount + onPage - 1) / onPage;
        List<Integer> trainIDs = trainDAO.getIdByStationNameList(stationName, page, onPage);
        List<TrainTime> trainsTimes = new ArrayList<>();
        String stopTime = "";
        for (Integer id : trainIDs) {
            int trainNumber = trainDAO.getTrainNumberByIdSingle(id);
            stopTime = stationTrainDAO.getStopTimeByTrainIdAndStationNameSingle(id, stationName);
            stopTime = TimeSupport.LongToTime(TimeToLong(stopTime) - 10800000);
            trainsTimes.add(new TrainTime(trainNumber, stopTime));
        }
        String toTimeBoardString = stationName + "/" + pagesCount + "/";
        for (TrainTime trainTime : trainsTimes) {
            toTimeBoardString += trainTime.getNumber();
            toTimeBoardString += "/";
            toTimeBoardString += trainTime.getTime();
            toTimeBoardString += "/";
        }
        messageSender.sendMessage(toTimeBoardString);
    }

}