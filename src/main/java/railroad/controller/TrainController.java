package railroad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import railroad.model.Train;
import railroad.service.TrainService;

import java.sql.Time;
import java.util.List;

@Controller
public class TrainController {
    private TrainService trainService;

    @Autowired
    public void setTrainService(TrainService trainService) {
        this.trainService = trainService;
    }

    @RequestMapping(value = "/alltrains", method = RequestMethod.GET)
    public ModelAndView allTrains() {
        List<Train> trains = trainService.allTrains();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("trains");
        modelAndView.addObject("trainsList", trains);
        return modelAndView;
    }

    @RequestMapping(value = "/trainsbystation", method = RequestMethod.POST)
    public ModelAndView trainsByStation(@ModelAttribute("station") String stationName) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("trainsbystation");
        modelAndView.addObject("StationName", stationName);
        modelAndView.addObject("NumberTimeList", trainService.trainsByStation(stationName));
        return modelAndView;
    }

    @RequestMapping(value = "/trainsbysearch", method = RequestMethod.POST)
    public ModelAndView trainsBySearch(@ModelAttribute("departureStation") String departureStationName, @ModelAttribute("arrivalStation") String arrivalStationName, @ModelAttribute("lowerTime") String lowerTimeString, @ModelAttribute("upperTime") String upperTimeString) {
        Time lowerTime = new Time((Integer.parseInt(lowerTimeString.substring(0, 2)) * 60 + Integer.parseInt(lowerTimeString.substring(3))) * 60000);
        Time upperTime = new Time((Integer.parseInt(upperTimeString.substring(0, 2)) * 60 + Integer.parseInt(upperTimeString.substring(3))) * 60000);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("trainsbysearch");
        modelAndView.addObject("DepartureStationName", departureStationName);
        modelAndView.addObject("ArrivalStationName", arrivalStationName);
        modelAndView.addObject("TrainSearchList", trainService.trainsBySearch(departureStationName, arrivalStationName, lowerTime, upperTime));
        return modelAndView;
    }

    @RequestMapping(value = "/addtrain", method = RequestMethod.POST)
    public ModelAndView addTrain(@ModelAttribute("train") Train train) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        trainService.add(train);
        return modelAndView;
    }
}
