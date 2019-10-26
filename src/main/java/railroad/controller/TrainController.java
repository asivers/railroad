package railroad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import railroad.model.additional.TrainTime;
import railroad.service.TrainService;
import java.sql.Time;
import java.util.List;

@Controller
@SessionAttributes({"TrainsByStation","TrainsBySearch"})
public class TrainController {
    private TrainService trainService;
    @Autowired
    public void setTrainService(TrainService trainService) {
        this.trainService = trainService;
    }

    private int trainsByStationPage;
    @RequestMapping(value = "/trainsbystation", method = RequestMethod.POST)
    public ModelAndView trainsByStation(@ModelAttribute("station") String stationName, @RequestParam(defaultValue = "1") int page) {
        int prevPage = page - 1;
        int nextPage = page + 1;
        int trainsCount = trainService.trainsByStationCount(stationName);
        int pagesCount = (trainsCount + 9)/10;
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/trainsbystation");
        modelAndView.addObject("TrainsByStation", trainService.trainsByStation(stationName, page));
        this.trainsBySearchPage = page;
        return modelAndView;
    }

    @RequestMapping(value = "/trainsbystation", method = RequestMethod.GET)
    public ModelAndView trainsByStationOutput(@ModelAttribute("TrainsByStation") List<TrainTime> trainsByStation) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("trainsbystation");
//        modelAndView.addObject("StationName", stationName);
        modelAndView.addObject("NumberTimeList", trainsByStation);
        return modelAndView;
    }

    private int trainsBySearchPage;
    @RequestMapping(value = "/trainsbysearch", method = RequestMethod.POST)
    public ModelAndView trainsBySearch(@ModelAttribute("departureStation") String departureStationName, @ModelAttribute("arrivalStation") String arrivalStationName, @ModelAttribute("lowerTime") String lowerTimeString, @ModelAttribute("upperTime") String upperTimeString, @RequestParam(defaultValue = "1") int page) {
        Time lowerTime = new Time((Integer.parseInt(lowerTimeString.substring(0, 2)) * 60 + Integer.parseInt(lowerTimeString.substring(3))) * 60000);
        Time upperTime = new Time((Integer.parseInt(upperTimeString.substring(0, 2)) * 60 + Integer.parseInt(upperTimeString.substring(3))) * 60000);
        int prevPage = page - 1;
        int nextPage = page + 1;
        int trainsCount = trainService.trainsBySearchCount(departureStationName, arrivalStationName, lowerTime, upperTime);
        int pagesCount = (trainsCount + 9)/10;
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/trainsbysearch");
//        modelAndView.addObject("DepartureStationName", departureStationName);
//        modelAndView.addObject("ArrivalStationName", arrivalStationName);
        modelAndView.addObject("TrainsBySearch", trainService.trainsBySearch(departureStationName, arrivalStationName, lowerTime, upperTime, page));
        return modelAndView;
    }

    @RequestMapping(value = "/trainsbysearch", method = RequestMethod.GET)
    public ModelAndView trainsBySearchOutput(@ModelAttribute("TrainsBySearch") List<TrainTime> trainsBySearch) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("trainsbysearch");
//        modelAndView.addObject("StationName", stationName);
        modelAndView.addObject("TrainSearchList", trainsBySearch);
        return modelAndView;
    }

}