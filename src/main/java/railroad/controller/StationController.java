package railroad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import railroad.model.Station;
import railroad.service.StationService;
import java.util.List;

@Controller
public class StationController {
    private StationService stationService;

    @Autowired
    public void setStationService(StationService stationService) {
        this.stationService = stationService;
    }

    @RequestMapping(value = "/allstations", method = RequestMethod.GET)
    public ModelAndView allStations() {
        List<Station> stations = stationService.allStations();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("stations");
        modelAndView.addObject("stationsList", stations);
        return modelAndView;
    }

    @RequestMapping(value = "/stationsbytrain", method = RequestMethod.POST)
    public ModelAndView stationsByTrain(@ModelAttribute("train") int trainNumber) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("stationsbytrain");
        modelAndView.addObject("TrainNumber", trainNumber);
        modelAndView.addObject("StationTimeList", stationService.stationsByTrain(trainNumber));
        return modelAndView;
    }

    @RequestMapping(value = "/addstation", method = RequestMethod.POST)
    public ModelAndView addStation(@ModelAttribute("station") Station station) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        stationService.add(station);
        return modelAndView;
    }
}
