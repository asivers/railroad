package railroad.controller;

//import org.apache.logging.log4j.Logger;
//import org.apache.logging.log4j.LogManager;
import org.apache.log4j.Logger;
//import org.apache.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import railroad.bean.NotificationManagedBean;
import railroad.dao.impl.TimeSupport;
import railroad.model.User;
import railroad.model.additional.TrainTime;
import railroad.model.login.RailroadUserDetails;
import railroad.service.*;

import javax.servlet.http.HttpSession;
import java.sql.Time;
import java.util.Date;
import java.util.List;

@Controller
@SessionAttributes({"currentUser", "currentUserID"})
public class MainController {

    private static final Logger log = Logger.getLogger(MainController.class);

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

    private TicketService ticketService;
    @Autowired
    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    private NewUserService newUserService;
    @Autowired
    public void setNewUserService(NewUserService newUserService) {
        this.newUserService = newUserService;
    }

    private NotificationManagedBean notificationManagedBean = new NotificationManagedBean();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView indexPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        log.info("index page visited");
        return modelAndView;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView signUp() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("signup");
        return modelAndView;
    }

    @RequestMapping(value = "/createuser", method = RequestMethod.POST)
    public ModelAndView createUser(@ModelAttribute("username") String username, @ModelAttribute("password") String password) {
        ModelAndView modelAndView = new ModelAndView();
        if (username.isEmpty() || password.isEmpty())
            modelAndView.setViewName("incorrectloginorpassword");
        else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(password);
            boolean isInDatabase = newUserService.isInDatabase(username, hashedPassword);
            if (isInDatabase)
                modelAndView.setViewName("useralreadyexists");
            else
                modelAndView.setViewName("registrationsuccess");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public ModelAndView signIn() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("signin");
        return modelAndView;
    }

    @RequestMapping(value = "/wrongloginpassword", method = RequestMethod.GET)
    public ModelAndView wrongLoginPassword() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("wrongloginpassword");
        return modelAndView;
    }

    @PostMapping("/postlogin")
    public ModelAndView postLogin(ModelAndView modelAndView, HttpSession session) {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        validatePrinciple(authentication.getPrincipal());
        User loggedInUser = ((RailroadUserDetails) authentication.getPrincipal()).getUserDetails();
        modelAndView.addObject("currentUserID", loggedInUser.getId());
        modelAndView.addObject("currentUser", loggedInUser.getUsername());
        session.setAttribute("userID", loggedInUser.getId());
        String role = authentication.toString().substring(authentication.toString().length() - 10);
        if (role.equals("ROLE_ADMIN"))
            modelAndView.setViewName("redirect:/adminmain");
        if (role.equals(" ROLE_USER"))
            modelAndView.setViewName("redirect:/usermain");
        return modelAndView;
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public ModelAndView logOut(SessionStatus session) {
        ModelAndView modelAndView = new ModelAndView();
        SecurityContextHolder.getContext().setAuthentication(null);
        session.setComplete();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    private void validatePrinciple(Object principal) {
        if (!(principal instanceof RailroadUserDetails)) {
            throw new  IllegalArgumentException("Principal can not be null!");
        }
    }

    @RequestMapping(value = "/usermain", method = RequestMethod.GET)
    public ModelAndView userMain() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("usermain");
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

    @RequestMapping(value = "/trainsbystation", method = RequestMethod.POST)
    public ModelAndView trainsByStation(@ModelAttribute("station") String stationName, @ModelAttribute("page") int page) {
        ModelAndView modelAndView = new ModelAndView();
        int trainsCount = trainService.trainsByStationCount(stationName);
        if (trainsCount == 0)
            modelAndView.setViewName("notrainsfound");
        else {
            int pagesCount = (trainsCount + 6) / 7;
            modelAndView.setViewName("trainsbystation");
            modelAndView.addObject("TrainsByStation", trainService.trainsByStation(stationName, page));
            modelAndView.addObject("StationName", stationName);
            modelAndView.addObject("Page", page);
            modelAndView.addObject("PagesCount", pagesCount);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/trainsbysearch", method = RequestMethod.POST)
    public ModelAndView trainsBySearch(@ModelAttribute("departureStation") String departureStationName, @ModelAttribute("arrivalStation") String arrivalStationName, @ModelAttribute("lowerTime") String lowerTimeString, @ModelAttribute("upperTime") String upperTimeString, @RequestParam(defaultValue = "1") int page) {
        ModelAndView modelAndView = new ModelAndView();
        Time lowerTime = new Time((Integer.parseInt(lowerTimeString.substring(0, 2)) * 60 + Integer.parseInt(lowerTimeString.substring(3))) * 60000);
        Time upperTime = new Time((Integer.parseInt(upperTimeString.substring(0, 2)) * 60 + Integer.parseInt(upperTimeString.substring(3))) * 60000);
        int trainsCount = trainService.trainsBySearchCount(departureStationName, arrivalStationName, lowerTime, upperTime);
        if (trainsCount == 0)
            modelAndView.setViewName("notrainsfound");
        else {
            int pagesCount = (trainsCount + 6) / 7;
            modelAndView.setViewName("trainsbysearch");
            modelAndView.addObject("TrainsBySearch", trainService.trainsBySearch(departureStationName, arrivalStationName, lowerTime, upperTime, page));
            modelAndView.addObject("DepartureStationName", departureStationName);
            modelAndView.addObject("LowerTime", lowerTime);
            modelAndView.addObject("UpperTime", upperTime);
            modelAndView.addObject("ArrivalStationName", arrivalStationName);
            modelAndView.addObject("Page", page);
            modelAndView.addObject("PagesCount", pagesCount);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/stationsbytrain", method = RequestMethod.POST)
    public ModelAndView stationsByTrain(@ModelAttribute("train") int trainNumber, @ModelAttribute("page") int page) {
        ModelAndView modelAndView = new ModelAndView();
        int stationsCount = stationService.stationsByTrainCount(trainNumber);
        if (stationsCount == 0)
            modelAndView.setViewName("nostationsfound");
        else {
            int pagesCount = (stationsCount + 7) / 8;
            modelAndView.setViewName("stationsbytrain");
            modelAndView.addObject("StationsByTrain", stationService.stationsByTrain(trainNumber, page));
            modelAndView.addObject("TrainNumber", trainNumber);
            modelAndView.addObject("Page", page);
            modelAndView.addObject("PagesCount", pagesCount);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/buyticket", method = RequestMethod.POST)
    public ModelAndView buyTicket(@ModelAttribute("train") int trainNumber, @ModelAttribute("departureTime") String departureTime) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("buyticket");
        modelAndView.addObject("TrainNumber", trainNumber);
        modelAndView.addObject("DepartureTime", departureTime);
        return modelAndView;
    }

    @RequestMapping(value = "/finishbuyticket", method = RequestMethod.POST)
    public ModelAndView finishBuyTicket(@ModelAttribute("currentUserID") int currentUserID, @ModelAttribute("firstName") String firstName, @ModelAttribute("secondName") String secondName, @ModelAttribute("birthDate") String birthDate, @ModelAttribute("trainNumber") int trainNumber, @ModelAttribute("departureTime") String departureTime) {
        ModelAndView modelAndView = new ModelAndView();
        String currentTime = (new Date()).toString().substring(11, 16);
        if (TimeSupport.TimeToLong(departureTime) - TimeSupport.TimeToLong(currentTime) < 600000)
            modelAndView.setViewName("ticketfaillate");
        else if (!(trainService.freeSeats(trainNumber)))
            modelAndView.setViewName("ticketfailnoseats");
        else
        if (passengerService.isOnTrain(firstName, secondName, birthDate, trainNumber, currentUserID))
            modelAndView.setViewName("ticketfailsame");
        else
            modelAndView.setViewName("ticketsuccess");
        return modelAndView;
    }

    @RequestMapping(value = "/mytickets", method = RequestMethod.POST)
    public ModelAndView ticketsByUser(@ModelAttribute("currentUserID") int currentUserID, @ModelAttribute("page") int page) {
        ModelAndView modelAndView = new ModelAndView();
        int ticketsCount = ticketService.ticketsByUserCount(currentUserID);
        if (ticketsCount == 0)
            modelAndView.setViewName("noticketsfound");
        else {
            int pagesCount = (ticketsCount + 7) / 8;
            modelAndView.setViewName("mytickets");
            modelAndView.addObject("TicketsList", ticketService.ticketsByUser(currentUserID, page));
            modelAndView.addObject("Page", page);
            modelAndView.addObject("PagesCount", pagesCount);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/adminmain", method = RequestMethod.GET)
    public ModelAndView adminMain() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("adminmain");
        return modelAndView;
    }

    @RequestMapping(value = "/addtrain", method = RequestMethod.GET)
    public ModelAndView addTrain() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("addtrain");
        return modelAndView;
    }

    @RequestMapping(value = "/finishaddtrain", method = RequestMethod.POST)
    public ModelAndView finishAddTrain(@ModelAttribute("train") int trainNumber, @ModelAttribute("seats") int seats) {
        ModelAndView modelAndView = new ModelAndView();
        if (!(trainService.isExist(trainNumber, seats))) {
            modelAndView.setViewName("trainaddsuccess");
            modelAndView.addObject("TrainNumber", trainNumber);
        }
        else
            modelAndView.setViewName("trainaddfail");
        return modelAndView;
    }

    @RequestMapping(value = "/addstationfortrain", method = RequestMethod.POST)
    public ModelAndView addStationForTrain(@ModelAttribute("train") int trainNumber) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("addstationfortrain");
        modelAndView.addObject("TrainNumber", trainNumber);
        return modelAndView;
    }

    @RequestMapping(value = "/finishaddstationfortrain", method = RequestMethod.POST)
    public ModelAndView finishAddStationForTrain(@ModelAttribute("train") int trainNumber, @ModelAttribute("station") String stationName, @ModelAttribute("stopTime") String stopTime) {
        ModelAndView modelAndView = new ModelAndView();
        if (!(stationService.isExistForTrain(trainNumber, stationName, stopTime))) {
            modelAndView.setViewName("stationfortrainaddsuccess");
            modelAndView.addObject("TrainNumber", trainNumber);
            modelAndView.addObject("LastStation", stationName);
            modelAndView.addObject("StopTime", stopTime);
            int trainsCount = trainService.trainsByStationCount(stationName);
            int pagesCount = (trainsCount + 6) / 7;
            List<TrainTime> toTimeBoard = trainService.trainsByStation(stationName, 1);
            String toTimeBoardString = stationName + "/" + pagesCount + "/";
            for (TrainTime trainTime : toTimeBoard) {
                toTimeBoardString += trainTime.getNumber();
                toTimeBoardString += "/";
                toTimeBoardString += trainTime.getTime();
                toTimeBoardString += "/";
            }
            notificationManagedBean.setMessage(toTimeBoardString);
            notificationManagedBean.sendNotification();
        }
        else {
            modelAndView.setViewName("stationfortrainaddfail");
            modelAndView.addObject("TrainNumber", trainNumber);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/addstation", method = RequestMethod.GET)
    public ModelAndView addStation() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("addstation");
        return modelAndView;
    }

    @RequestMapping(value = "/finishaddstation", method = RequestMethod.POST)
    public ModelAndView finishAddStation(@ModelAttribute("station") String stationName) {
        ModelAndView modelAndView = new ModelAndView();
        if (!(stationService.isExist(stationName)))
            modelAndView.setViewName("stationaddsuccess");
        else
            modelAndView.setViewName("stationaddfail");
        return modelAndView;
    }

    @RequestMapping(value = "/alltrains", method = RequestMethod.POST)
    public ModelAndView allTrains(@ModelAttribute("page") int page) {
        ModelAndView modelAndView = new ModelAndView();
        int trainsCount = trainService.allTrainsCount();
        if (trainsCount == 0)
            modelAndView.setViewName("notrainsfoundadmin");
        else {
            int pagesCount = (trainsCount + 6) / 7;
            modelAndView.setViewName("alltrains");
            modelAndView.addObject("AllTrainsList", trainService.allTrains(page));
            modelAndView.addObject("Page", page);
            modelAndView.addObject("PagesCount", pagesCount);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/choosetrain", method = RequestMethod.GET)
    public ModelAndView chooseTrain() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("choosetrain");
        return modelAndView;
    }

    @RequestMapping(value = "/passengersbytrain", method = RequestMethod.POST)
    public ModelAndView allTrains(@ModelAttribute("train") int trainNumber, @ModelAttribute("page") int page) {
        ModelAndView modelAndView = new ModelAndView();
        int passengersCount = passengerService.passengersByTrainCount(trainNumber);
        if (passengersCount == 0)
            modelAndView.setViewName("nopassengersfound");
        else {
            int pagesCount = (passengersCount + 7) / 8;
            modelAndView.setViewName("passengersbytrain");
            modelAndView.addObject("PassengersList", passengerService.passengersByTrain(trainNumber, page));
            modelAndView.addObject("TrainNumber", trainNumber);
            modelAndView.addObject("Page", page);
            modelAndView.addObject("PagesCount", pagesCount);
        }
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("exception");
        return modelAndView;
    }

    @RequestMapping(value = "/403")
    public ModelAndView accessDeniedException() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("403");
        return modelAndView;
    }

    @RequestMapping(value = "/404")
    public ModelAndView resourceNotFoundException() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404");
        return modelAndView;
    }

}