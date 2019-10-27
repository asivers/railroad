package railroad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import railroad.service.PassengerService;
import railroad.service.StationService;
import railroad.service.TrainService;

@Controller
@SessionAttributes("admin")
public class AdminController {

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
            modelAndView.setViewName("wrongloginpassword");
        return modelAndView;
    }

    @RequestMapping(value = "/wrongloginpassword", method = RequestMethod.GET)
    public ModelAndView wrongLoginPassword() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("wrongloginpassword");
        return modelAndView;
    }

    @RequestMapping(value = "/addtrain", method = RequestMethod.GET)
    public ModelAndView addTrain(@ModelAttribute("admin") boolean admin) {
        ModelAndView modelAndView = new ModelAndView();
        if (admin)
            modelAndView.setViewName("addtrain");
        else
            modelAndView.setViewName("wrongloginpassword");
        return modelAndView;
    }

    @RequestMapping(value = "/finishAddTrain", method = RequestMethod.POST)
    public ModelAndView finishAddTrain(@ModelAttribute("admin") boolean admin, @ModelAttribute("train") int trainNumber) {
        ModelAndView modelAndView = new ModelAndView();
        if (admin)
            if (!(trainService.isExist(trainNumber)))
                modelAndView.setViewName("trainaddsuccess");
            else
                modelAndView.setViewName("trainaddfail");
        else
            modelAndView.setViewName("wrongloginpassword");
        return modelAndView;
    }

    @RequestMapping(value = "/addstation", method = RequestMethod.GET)
    public ModelAndView addStation(@ModelAttribute("admin") boolean admin) {
        ModelAndView modelAndView = new ModelAndView();
        if (admin)
            modelAndView.setViewName("addstation");
        else
            modelAndView.setViewName("wrongloginpassword");
        return modelAndView;
    }

    @RequestMapping(value = "/finishAddStation", method = RequestMethod.POST)
    public ModelAndView finishAddStation(@ModelAttribute("admin") boolean admin, @ModelAttribute("station") String stationName) {
        ModelAndView modelAndView = new ModelAndView();
        if (admin)
            if (!(stationService.isExist(stationName)))
                modelAndView.setViewName("stationaddsuccess");
            else
                modelAndView.setViewName("stationaddfail");
        else
            modelAndView.setViewName("wrongloginpassword");
        return modelAndView;
    }

    @RequestMapping(value = "/alltrains", method = RequestMethod.POST)
    public ModelAndView allTrains(@ModelAttribute("admin") boolean admin, @ModelAttribute("page") int page) {
        ModelAndView modelAndView = new ModelAndView();
        if (admin) {
            int trainsCount = trainService.allTrainsCount();
            int pagesCount = (trainsCount + 6)/7;
            modelAndView.setViewName("alltrains");
            modelAndView.addObject("AllTrainsList", trainService.allTrains(page));
            modelAndView.addObject("Page", page);
            modelAndView.addObject("PagesCount", pagesCount);
        }
        else
            modelAndView.setViewName("wrongloginpassword");
        return modelAndView;
    }

    @RequestMapping(value = "/choosetrain", method = RequestMethod.GET)
    public ModelAndView chooseTrain(@ModelAttribute("admin") boolean admin) {
        ModelAndView modelAndView = new ModelAndView();
        if (admin)
            modelAndView.setViewName("choosetrain");
        else
            modelAndView.setViewName("wrongloginpassword");
        return modelAndView;
    }

    @RequestMapping(value = "/passengersbytrain", method = RequestMethod.POST)
    public ModelAndView allTrains(@ModelAttribute("admin") boolean admin, @ModelAttribute("train") int trainNumber, @ModelAttribute("page") int page) {
        ModelAndView modelAndView = new ModelAndView();
        if (admin) {
            int passengersCount = passengerService.passengersByTrainCount(trainNumber);
            int pagesCount = (passengersCount + 7)/8;
            modelAndView.setViewName("passengersbytrain");
            modelAndView.addObject("PassengersList", passengerService.passengersByTrain(trainNumber, page));
            modelAndView.addObject("TrainNumber", trainNumber);
            modelAndView.addObject("Page", page);
            modelAndView.addObject("PagesCount", pagesCount);
        }
        else
            modelAndView.setViewName("wrongloginpassword");
        return modelAndView;
    }

}