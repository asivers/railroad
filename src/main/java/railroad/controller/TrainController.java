package railroad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import railroad.service.TrainService;
import java.sql.Time;

@Controller
public class TrainController {
    private TrainService trainService;
    @Autowired
    public void setTrainService(TrainService trainService) {
        this.trainService = trainService;
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
}