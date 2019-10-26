package railroad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView indexPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping(value = "/usermain", method = RequestMethod.GET)
    public ModelAndView userMain() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("usermain");
        return modelAndView;
    }

    @RequestMapping(value = "/adminlogin", method = RequestMethod.GET)
    public ModelAndView adminLogin() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("adminlogin");
        return modelAndView;
    }

    @RequestMapping(value = "/findtrain", method = RequestMethod.GET)
    public ModelAndView findTrain() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("findtrain");
        return modelAndView;
    }

    @RequestMapping(value = "/choosestation", method = RequestMethod.GET)
    public ModelAndView chooseStation() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("choosestation");
        return modelAndView;
    }

}