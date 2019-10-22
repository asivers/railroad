package railroad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import railroad.model.Station;
import railroad.model.Train;
import railroad.model.TrainTime;
import railroad.service.TrainService;
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
//        List<TrainTime> trainsByStation = trainService.trainsByStation(stationName);
        List<Integer> trainsByStation = trainService.trainsByStation(stationName);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/schedulestation");
        modelAndView.addObject("trainsByStationList", trainsByStation);
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
