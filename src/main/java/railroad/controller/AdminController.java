package railroad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("admin")
public class AdminController {

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
            modelAndView.setViewName("index");
        return modelAndView;
    }
    @RequestMapping(value = "/addtrain", method = RequestMethod.GET)
    public ModelAndView addTrain(@ModelAttribute("admin") boolean admin) {
        ModelAndView modelAndView = new ModelAndView();
        if (admin)
            modelAndView.setViewName("addtrain");
        else
            modelAndView.setViewName("index");
        return modelAndView;
    }

}