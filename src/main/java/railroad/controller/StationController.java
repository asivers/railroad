package railroad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import railroad.model.additional.StationTime;
import railroad.service.StationService;

import java.util.ArrayList;
import java.util.List;

@Controller
//@SessionAttributes({"StationsByTrain", "TrainNumber", "Page", "PagesCount"})
public class StationController {
    private StationService stationService;
    @Autowired
    public void setStationService(StationService stationService) {
        this.stationService = stationService;
    }

//    private int stationsByTrainPage;
//    @RequestMapping(value = "/stationsbytrain", method = RequestMethod.POST)
//    public ModelAndView stationsByTrain(@ModelAttribute("train") int trainNumber, @RequestParam(defaultValue = "1") int page) {
//        this.stationsByTrainPage = page;
//        int stationsCount = stationService.stationsByTrainCount(trainNumber);
//        int pagesCount = (stationsCount + 9)/10;
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("redirect:/stationsbytrain/" + this.stationsByTrainPage);
//        modelAndView.addObject("StationsByTrain", stationService.stationsByTrain(trainNumber, page));
//        modelAndView.addObject("TrainNumber", trainNumber);
//        modelAndView.addObject("Page", page);
//        modelAndView.addObject("PagesCount", pagesCount);
//        return modelAndView;
//    }
//
//    @RequestMapping(value = "/stationsbytrain", method = RequestMethod.GET)
//    public ModelAndView stationsByTrainOutput(@ModelAttribute("StationsByTrain") List<StationTime> stationsByTrain, @ModelAttribute("TrainNumber") int trainNumber, @ModelAttribute("Page") int page, @ModelAttribute("PagesCount") int pagesCount) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("stationsbytrain");
////        modelAndView.addObject("StationTimeList", stationsByTrain);
////        modelAndView.addObject("TrainNumber", trainNumber);
////        modelAndView.addObject("Page", page);
////        modelAndView.addObject("PagesCount", pagesCount);
//        return modelAndView;
//    }

    private int stationsByTrainPage;
    @RequestMapping(value = "/stationsbytrain", method = RequestMethod.POST)
    public ModelAndView stationsByTrain(@ModelAttribute("train") int trainNumber, @ModelAttribute("page") int page) {
//        this.stationsByTrainPage = page;
        int stationsCount = stationService.stationsByTrainCount(trainNumber);
        int pagesCount = (stationsCount + 9)/10;
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("stationsbytrain");
        modelAndView.addObject("StationsByTrain", stationService.stationsByTrain(trainNumber, page));
        modelAndView.addObject("TrainNumber", trainNumber);
        modelAndView.addObject("Page", page);
        modelAndView.addObject("PagesCount", pagesCount);
        return modelAndView;
    }

//    @RequestMapping(value = "/stationsbytrain", method = RequestMethod.GET)
//    public ModelAndView stationsByTrainOutput(@ModelAttribute("StationsByTrain") List<StationTime> stationsByTrain, @ModelAttribute("TrainNumber") int trainNumber, @ModelAttribute("Page") int page, @ModelAttribute("PagesCount") int pagesCount) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("stationsbytrain");
//        modelAndView.addObject("StationTimeList", stationsByTrain);
//        modelAndView.addObject("TrainNumber", trainNumber);
//        modelAndView.addObject("Page", page);
//        modelAndView.addObject("PagesCount", pagesCount);
//        return modelAndView;
//    }

}