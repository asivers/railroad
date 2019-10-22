package railroad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import railroad.model.Passenger;
import railroad.service.PassengerService;
import java.util.List;

@Controller
public class PassengerController {
    private PassengerService passengerService;

    @Autowired
    public void setPassengerService(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @RequestMapping(value = "/allpassengers", method = RequestMethod.GET)
    public ModelAndView allPassengers() {
        List<Passenger> passengers = passengerService.allPassengers();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("passengers");
        modelAndView.addObject("passengersList", passengers);
        return modelAndView;
    }

    @RequestMapping(value = "/addpassenger", method = RequestMethod.POST)
    public ModelAndView addPassenger(@ModelAttribute("passenger") Passenger passenger) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        passengerService.add(passenger);
        return modelAndView;
    }
}
