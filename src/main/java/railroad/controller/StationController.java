package railroad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import railroad.service.StationService;

@Controller
public class StationController {
    private StationService stationService;
    @Autowired
    public void setStationService(StationService stationService) {
        this.stationService = stationService;
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