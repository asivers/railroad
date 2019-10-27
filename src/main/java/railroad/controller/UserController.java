package railroad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import railroad.dao.impl.TimeSupport;
import railroad.service.PassengerService;
import railroad.service.StationService;
import railroad.service.TrainService;
import java.sql.Time;
import java.util.Date;

@Controller
public class UserController {

    private TrainService trainService;
    @Autowired
    public void setTrainService(TrainService trainService) {
        this.trainService = trainService;
    }

    private StationService stationService;
    @Autowired
    public void setStationService(StationService stationService) {
        this.stationService = stationService;
    }

    private PassengerService passengerService;
    @Autowired
    public void setPassengerService(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView indexPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping(value = "/usermain", method = RequestMethod.GET)
    public ModelAndView userMain() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("usermain");
        return modelAndView;
    }

    @RequestMapping(value = "/adminlogin", method = RequestMethod.GET)
    public ModelAndView adminLogin() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("adminlogin");
        return modelAndView;
    }

    @RequestMapping(value = "/findtrain", method = RequestMethod.GET)
    public ModelAndView findTrain() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("findtrain");
        return modelAndView;
    }

    @RequestMapping(value = "/choosestation", method = RequestMethod.GET)
    public ModelAndView chooseStation() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("choosestation");
        return modelAndView;
    }

    @RequestMapping(value = "/trainsbystation", method = RequestMethod.POST)
    public ModelAndView trainsByStation(@ModelAttribute("station") String stationName, @RequestParam(defaultValue = "1") int page) {
        int trainsCount = trainService.trainsByStationCount(stationName);
        int pagesCount = (trainsCount + 6)/7;
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("trainsbystation");
        modelAndView.addObject("TrainsByStation", trainService.trainsByStation(stationName, page));
        modelAndView.addObject("StationName", stationName);
        modelAndView.addObject("Page", page);
        modelAndView.addObject("PagesCount", pagesCount);
        return modelAndView;
    }

    @RequestMapping(value = "/trainsbysearch", method = RequestMethod.POST)
    public ModelAndView trainsBySearch(@ModelAttribute("departureStation") String departureStationName, @ModelAttribute("arrivalStation") String arrivalStationName, @ModelAttribute("lowerTime") String lowerTimeString, @ModelAttribute("upperTime") String upperTimeString, @RequestParam(defaultValue = "1") int page) {
        Time lowerTime = new Time((Integer.parseInt(lowerTimeString.substring(0, 2)) * 60 + Integer.parseInt(lowerTimeString.substring(3))) * 60000);
        Time upperTime = new Time((Integer.parseInt(upperTimeString.substring(0, 2)) * 60 + Integer.parseInt(upperTimeString.substring(3))) * 60000);
        int trainsCount = trainService.trainsBySearchCount(departureStationName, arrivalStationName, lowerTime, upperTime);
        int pagesCount = (trainsCount + 6)/7;
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("trainsbysearch");
        modelAndView.addObject("TrainsBySearch", trainService.trainsBySearch(departureStationName, arrivalStationName, lowerTime, upperTime, page));
        modelAndView.addObject("DepartureStationName", departureStationName);
        modelAndView.addObject("LowerTime", lowerTime);
        modelAndView.addObject("UpperTime", upperTime);
        modelAndView.addObject("ArrivalStationName", arrivalStationName);
        modelAndView.addObject("Page", page);
        modelAndView.addObject("PagesCount", pagesCount);
        return modelAndView;
    }

    @RequestMapping(value = "/stationsbytrain", method = RequestMethod.POST)
    public ModelAndView stationsByTrain(@ModelAttribute("train") int trainNumber, @ModelAttribute("page") int page) {
        int stationsCount = stationService.stationsByTrainCount(trainNumber);
        int pagesCount = (stationsCount + 7)/8;
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("stationsbytrain");
        modelAndView.addObject("StationsByTrain", stationService.stationsByTrain(trainNumber, page));
        modelAndView.addObject("TrainNumber", trainNumber);
        modelAndView.addObject("Page", page);
        modelAndView.addObject("PagesCount", pagesCount);
        return modelAndView;
    }

    @RequestMapping(value = "/buyticket", method = RequestMethod.POST)
    public ModelAndView buyTicket(@ModelAttribute("train") int trainNumber, @ModelAttribute("departureTime") String departureTime) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("buyticket");
        modelAndView.addObject("TrainNumber", trainNumber);
        modelAndView.addObject("DepartureTime", departureTime);
        return modelAndView;
    }

    @RequestMapping(value = "/finishbuyticket", method = RequestMethod.POST)
    public ModelAndView finishBuyTicket(@ModelAttribute("firstName") String firstName, @ModelAttribute("secondName") String secondName, @ModelAttribute("birthDate") String birthDate, @ModelAttribute("trainNumber") int trainNumber, @ModelAttribute("departureTime") String departureTime) {
        ModelAndView modelAndView = new ModelAndView();
        String currentTime = (new Date()).toString().substring(11, 16);
        if (TimeSupport.TimeToLong(departureTime) - TimeSupport.TimeToLong(currentTime) < 600000)
            modelAndView.setViewName("ticketfaillate");
        else if (!(trainService.freeSeats(trainNumber)))
            modelAndView.setViewName("ticketfailnoseats");
        else
        if (passengerService.isOnTrain(firstName, secondName, birthDate, trainNumber))
            modelAndView.setViewName("ticketfailsame");
        else
            modelAndView.setViewName("ticketsuccess");
        return modelAndView;
    }

}