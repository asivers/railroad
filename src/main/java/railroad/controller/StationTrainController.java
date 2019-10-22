package railroad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import railroad.model.StationTrain;
import railroad.service.StationTrainService;
import java.util.List;

@Controller
public class StationTrainController {
    private StationTrainService stationTrainService;

    @Autowired
    public void setStationTrainService(StationTrainService stationTrainService) {
        this.stationTrainService = stationTrainService;
    }

    @RequestMapping(value = "/allstationTrains", method = RequestMethod.GET)
    public ModelAndView allStationTrains() {
        List<StationTrain> stationTrains = stationTrainService.allStationTrains();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("stationTrains");
        modelAndView.addObject("stationTrainsList", stationTrains);
        return modelAndView;
    }

    @RequestMapping(value = "/addstationTrain", method = RequestMethod.POST)
    public ModelAndView addStationTrain(@ModelAttribute("stationTrain") StationTrain stationTrain) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        stationTrainService.add(stationTrain);
        return modelAndView;
    }
}
