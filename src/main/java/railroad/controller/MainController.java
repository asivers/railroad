package railroad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import railroad.service.StationService;
import railroad.service.TrainService;
import java.sql.Time;

@Controller
@SessionAttributes("admin")
public class MainController {

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

    @RequestMapping(value = "/adminmain", method = RequestMethod.POST)
    public ModelAndView adminMainPost(@ModelAttribute("login") String login, @ModelAttribute("password") String password) {
        ModelAndView modelAndView = new ModelAndView();
        if ((login.equals("newapp@mail.ru")) && (password.equals("12345")))
            modelAndView.addObject("admin", true);
        else
            modelAndView.addObject("admin", false);
        modelAndView.setViewName("redirect:/adminmain");
        return modelAndView;
    }

    @RequestMapping(value = "/adminmain", method = RequestMethod.GET)
    public ModelAndView adminMain(@ModelAttribute("admin") boolean admin) {
        ModelAndView modelAndView = new ModelAndView();
        if (admin)
            modelAndView.setViewName("adminmain");
        else
            modelAndView.setViewName("index");
        return modelAndView;
    }
    @RequestMapping(value = "/addtrain", method = RequestMethod.GET)
    public ModelAndView addTrain(@ModelAttribute("admin") boolean admin) {
        ModelAndView modelAndView = new ModelAndView();
        if (admin)
            modelAndView.setViewName("addtrain");
        else
            modelAndView.setViewName("index");
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

}