package railroad.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import railroad.service.impl.TimeSupport;
import railroad.messaging.MessageSender;
import railroad.model.User;
import railroad.model.additional.TrainTime;
import railroad.model.login.RailroadUserDetails;
import railroad.service.*;

import javax.servlet.http.HttpSession;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@SessionAttributes({"currentUser", "currentUserID"})
public class MainController {

    private static final Logger log = Logger.getLogger(MainController.class);

    @Autowired
    MessageSender messageSender;

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

    /**
     * Go to index page.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView indexPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        log.info("index page visited");
        return modelAndView;
    }

    /**
     * Go to "sign up" page.
     */
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView signUp() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("signup");
        log.info("registration page visited");
        return modelAndView;
    }

    /**
     * Adds new user to the database if it does not exist
     * and if login and password are correct.
     *
     * @param username username
     * @param password password
     */
    @RequestMapping(value = "/createuser", method = RequestMethod.POST)
    public ModelAndView createUser(@ModelAttribute("username") String username, @ModelAttribute("password") String password) {
        ModelAndView modelAndView = new ModelAndView();
        if (!(isValidInput(username)) || !(isValidInput(password))) {
            modelAndView.setViewName("incorrectloginorpassword");
            log.error("incorrect login or password");
        }
        else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(password);
            boolean isInDatabase = newUserService.isInDatabase(username, hashedPassword);
            if (isInDatabase) {
                modelAndView.setViewName("useralreadyexists");
                log.info("user already exists");
            }
            else {
                modelAndView.setViewName("registrationsuccess");
                log.info("registration was successful");
            }
        }
        return modelAndView;
    }

    /**
     * Go to "sign in" page.
     */
    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public ModelAndView signIn() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("signin");
        log.info("authorization page visited");
        return modelAndView;
    }

    /**
     * Redirects here if the login and the password do not match.
     */
    @RequestMapping(value = "/wrongloginpassword", method = RequestMethod.GET)
    public ModelAndView wrongLoginPassword() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("wrongloginpassword");
        log.info("wrong login or password");
        return modelAndView;
    }

    /**
     * Spring Security post login method.
     *
     * @param modelAndView
     * @param session
     */
    @PostMapping("/postlogin")
    public ModelAndView postLogin(ModelAndView modelAndView, HttpSession session) {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        validatePrinciple(authentication.getPrincipal());
        User loggedInUser = ((RailroadUserDetails) authentication.getPrincipal()).getUserDetails();
        modelAndView.addObject("currentUserID", loggedInUser.getId());
        modelAndView.addObject("currentUser", loggedInUser.getUsername());
        session.setAttribute("userID", loggedInUser.getId());
        String role = authentication.toString().substring(authentication.toString().length() - 10);
        if (role.equals("ROLE_ADMIN")) {
            modelAndView.setViewName("redirect:/adminmain");
            log.info("authorization as admin was successful");
        }
        if (role.equals(" ROLE_USER")) {
            modelAndView.setViewName("redirect:/usermain");
            log.info("authorization as user was successful");
        }
        return modelAndView;
    }

    /**
     * Spring Security logout method.
     *
     * @param session
     */
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public ModelAndView logOut(SessionStatus session) {
        ModelAndView modelAndView = new ModelAndView();
        SecurityContextHolder.getContext().setAuthentication(null);
        session.setComplete();
        modelAndView.setViewName("index");
        log.info("logout was successful");
        return modelAndView;
    }

    /**
     * Spring Security validation.
     *
     * @param principal
     */
    private void validatePrinciple(Object principal) {
        if (!(principal instanceof RailroadUserDetails)) {
            throw new  IllegalArgumentException("Principal can not be null!");
        }
    }

    /**
     * Go to "usermain" page.
     */
    @RequestMapping(value = "/usermain", method = RequestMethod.GET)
    public ModelAndView userMain() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("usermain");
        log.info("main user page visited");
        return modelAndView;
    }

    /**
     * Go to "choose station" page.
     */
    @RequestMapping(value = "/choosestation", method = RequestMethod.GET)
    public ModelAndView chooseStation() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("choosestation");
        log.info("choose station page visited");
        return modelAndView;
    }

    /**
     * Uploads list of trains for this station.
     *
     * @param stationName station name
     * @param page page number
     */
    @RequestMapping(value = "/trainsbystation", method = RequestMethod.POST)
    public ModelAndView trainsByStation(@ModelAttribute("station") String stationName, @ModelAttribute("page") int page) {
        ModelAndView modelAndView = new ModelAndView();
        if (isValidInput(stationName)) {
            int trainsCount = trainService.trainsByStationCount(stationName);
            if (trainsCount == 0) {
                modelAndView.setViewName("notrainsfound");
                log.info("no trains found");
            }
            else {
                int pagesCount = (trainsCount + 6) / 7;
                modelAndView.setViewName("trainsbystation");
                modelAndView.addObject("TrainsByStation", trainService.trainsByStation(stationName, page));
                modelAndView.addObject("StationName", stationName);
                modelAndView.addObject("Page", page);
                modelAndView.addObject("PagesCount", pagesCount);
                log.info("" + trainsCount + " trains were found for station " + stationName);
            }
        }
        else {
            modelAndView.setViewName("userwronginput");
            log.error("trains by station - wrong input");
        }
        return modelAndView;
    }

    /**
     * Uploads list of stations for this train.
     *
     * @param trainNumber train number
     * @param page page number
     */
    @RequestMapping(value = "/stationsbytrain", method = RequestMethod.POST)
    public ModelAndView stationsByTrain(@ModelAttribute("train") int trainNumber, @ModelAttribute("page") int page) {
        ModelAndView modelAndView = new ModelAndView();
        int stationsCount = stationService.stationsByTrainCount(trainNumber);
        if (stationsCount == 0) {
            modelAndView.setViewName("nostationsfound");
            log.info("no stations found");
        }
        else {
            int pagesCount = (stationsCount + 7) / 8;
            modelAndView.setViewName("stationsbytrain");
            modelAndView.addObject("StationsByTrain", stationService.stationsByTrain(trainNumber, page));
            modelAndView.addObject("TrainNumber", trainNumber);
            modelAndView.addObject("Page", page);
            modelAndView.addObject("PagesCount", pagesCount);
            log.info("" + stationsCount + " stations were found for train " + trainNumber);
        }
        return modelAndView;
    }

    /**
     * Go to "find train" page.
     */
    @RequestMapping(value = "/findtrain", method = RequestMethod.GET)
    public ModelAndView findTrain() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("findtrain");
        log.info("find train page visited");
        return modelAndView;
    }

    /**
     * Uploads list of trains which go from departure station to arrival station
     * and arrive not earlier than lower time but not later than upper time.
     *
     * @param departureStationName departure station
     * @param arrivalStationName arrival station
     * @param lowerTimeString lower time
     * @param upperTimeString upper time
     * @param page page number
     */
    @RequestMapping(value = "/trainsbysearch", method = RequestMethod.POST)
    public ModelAndView trainsBySearch(@ModelAttribute("departureStation") String departureStationName, @ModelAttribute("arrivalStation") String arrivalStationName, @ModelAttribute("lowerTime") String lowerTimeString, @ModelAttribute("upperTime") String upperTimeString, @RequestParam(defaultValue = "1") int page) {
        ModelAndView modelAndView = new ModelAndView();
        if (isValidInput(departureStationName) && isValidInput(arrivalStationName) && isTime(lowerTimeString) && isTime(upperTimeString)) {
            int trainsCount = trainService.trainsBySearchCount(departureStationName, arrivalStationName, lowerTimeString, upperTimeString);
            if (trainsCount == 0) {
                modelAndView.setViewName("notrainsfound");
                log.info("no trains found");
            }
            else {
                int pagesCount = (trainsCount + 6) / 7;
                modelAndView.setViewName("trainsbysearch");
                modelAndView.addObject("TrainsBySearch", trainService.trainsBySearch(departureStationName, arrivalStationName, lowerTimeString, upperTimeString, page));
                modelAndView.addObject("DepartureStationName", departureStationName);
                modelAndView.addObject("ArrivalStationName", arrivalStationName);
                modelAndView.addObject("LowerTimeString", lowerTimeString);
                modelAndView.addObject("UpperTimeString", upperTimeString);
                modelAndView.addObject("Page", page);
                modelAndView.addObject("PagesCount", pagesCount);
                log.info("" + trainsCount + " trains were found by search conditions");
            }
        }
        else {
            modelAndView.setViewName("userwronginput");
            log.error("trains by search - wrong input");
        }
        return modelAndView;
    }

    /**
     * Go to "buy ticket" page with given conditions.
     *
     * @param trainNumber train number
     * @param departureTime departure time
     */
    @RequestMapping(value = "/buyticket", method = RequestMethod.POST)
    public ModelAndView buyTicket(@ModelAttribute("train") int trainNumber, @ModelAttribute("departureTime") String departureTime) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("buyticket");
        modelAndView.addObject("TrainNumber", trainNumber);
        modelAndView.addObject("DepartureTime", departureTime);
        log.info("buy ticket page for train " + trainNumber + " visited");
        return modelAndView;
    }

    /**
     * Purchases ticket for passenger if they are not late, there are free seats
     * in the train, and the passenger is unique.
     *
     * @param currentUserID current user id
     * @param firstName passenger first name
     * @param secondName passenger second name
     * @param birthDate passenger birth date
     * @param trainNumber train number
     * @param departureTime departure time
     */
    @RequestMapping(value = "/finishbuyticket", method = RequestMethod.POST)
    public ModelAndView finishBuyTicket(@ModelAttribute("currentUserID") int currentUserID, @ModelAttribute("firstName") String firstName, @ModelAttribute("secondName") String secondName, @ModelAttribute("birthDate") String birthDate, @ModelAttribute("trainNumber") int trainNumber, @ModelAttribute("departureTime") String departureTime) {
        ModelAndView modelAndView = new ModelAndView();
        if (isValidInput(firstName) && isValidInput(secondName) && isBirthDate(birthDate)) {
            String currentTime = (new Date()).toString().substring(11, 16);
            if (TimeSupport.TimeToLong(departureTime) - TimeSupport.TimeToLong(currentTime) < 600000) {
                modelAndView.setViewName("ticketfaillate");
                log.info("ticket not bought - too late");
            }
            else if (!(trainService.freeSeats(trainNumber))) {
                modelAndView.setViewName("ticketfailnoseats");
                log.info("ticket not bought - no free seats");
            }
            else if (passengerService.isOnTrain(firstName, secondName, birthDate, trainNumber, currentUserID)) {
                modelAndView.setViewName("ticketfailsame");
                log.info("ticket not bought - passenger with same data");
            }
            else {
                modelAndView.setViewName("ticketsuccess");
                log.info("ticket bought successfully");
            }
        }
        else {
            modelAndView.setViewName("userwronginput");
            log.error("buy ticket - wrong input");
        }
        return modelAndView;
    }

    /**
     * Uploads list of tickets for current user.
     *
     * @param currentUserID current user id
     * @param page page number
     */
    @RequestMapping(value = "/mytickets", method = RequestMethod.POST)
    public ModelAndView ticketsByUser(@ModelAttribute("currentUserID") int currentUserID, @ModelAttribute("page") int page) {
        ModelAndView modelAndView = new ModelAndView();
        int ticketsCount = ticketService.ticketsByUserCount(currentUserID);
        if (ticketsCount == 0) {
            modelAndView.setViewName("noticketsfound");
            log.info("no tickets found");
        }
        else {
            int pagesCount = (ticketsCount + 7) / 8;
            modelAndView.setViewName("mytickets");
            modelAndView.addObject("TicketsList", ticketService.ticketsByUser(currentUserID, page));
            modelAndView.addObject("Page", page);
            modelAndView.addObject("PagesCount", pagesCount);
            log.info("" + ticketsCount + " tickets were found for current user Id = " + currentUserID);
        }
        return modelAndView;
    }

    /**
     * Go to "admin main" page.
     */
    @RequestMapping(value = "/adminmain", method = RequestMethod.GET)
    public ModelAndView adminMain() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("adminmain");
        log.info("admin main page visited");
        return modelAndView;
    }

    /**
     * Go to "add train" page.
     */
    @RequestMapping(value = "/addtrain", method = RequestMethod.GET)
    public ModelAndView addTrain() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("addtrain");
        log.info("update train page visited");
        return modelAndView;
    }

    /**
     * Adds train to the database if it does not exist and sets the seats number.
     *
     * @param trainNumber train number
     * @param seats seats number
     */
    @RequestMapping(value = "/finishaddtrain", method = RequestMethod.POST)
    public ModelAndView finishAddTrain(@ModelAttribute("train") int trainNumber, @ModelAttribute("seats") int seats) {
        ModelAndView modelAndView = new ModelAndView();
        if (trainNumber >= 100000 && trainNumber <= 999999 && seats > 0 && seats < 10000) {
            if (!(trainService.isExist(trainNumber, seats))) {
                modelAndView.setViewName("trainaddsuccess");
                modelAndView.addObject("TrainNumber", trainNumber);
                modelAndView.addObject("Statement1", "Train " + trainNumber + " added!");
                log.info("train " + trainNumber + " added successfully");
            }
            else {
                modelAndView.setViewName("trainaddsuccess");
                modelAndView.addObject("TrainNumber", trainNumber);
                modelAndView.addObject("Statement1", "Update train " + trainNumber);
                log.info("train " + trainNumber + " chosen successfully");
            }
        }
        else {
            modelAndView.setViewName("adminwronginput");
            log.error("update train - wrong input");
        }
        return modelAndView;
    }

    /**
     * Go to "add station for train" page.
     */
    @RequestMapping(value = "/addstationfortrain", method = RequestMethod.POST)
    public ModelAndView addStationForTrain(@ModelAttribute("train") int trainNumber) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("addstationfortrain");
        modelAndView.addObject("TrainNumber", trainNumber);
        log.info("add station for train page visited");
        return modelAndView;
    }

    /**
     * Adds station to the train route if it does not exist and sets stop time.
     *
     * @param trainNumber train number
     * @param stationName stationName
     * @param stopTime stop time
     */
    @RequestMapping(value = "/finishaddstationfortrain", method = RequestMethod.POST)
    public ModelAndView finishAddStationForTrain(@ModelAttribute("train") int trainNumber, @ModelAttribute("station") String stationName, @ModelAttribute("stopTime") String stopTime) {
        ModelAndView modelAndView = new ModelAndView();
        if (isValidInput(stationName)) {
            if (!(stationService.isExistForTrain(trainNumber, stationName, stopTime))) {
                modelAndView.setViewName("trainaddsuccess");
                modelAndView.addObject("TrainNumber", trainNumber);
                modelAndView.addObject("Statement1", "Station for train " + trainNumber + " added!");
                modelAndView.addObject("Statement2", "Last added station: " + stationName + ", " + stopTime);
                log.info("station " + stationName + " added successfully for train " + trainNumber);
                toTimeBoardMethod(stationName);
            }
            else {
                modelAndView.setViewName("trainaddsuccess");
                modelAndView.addObject("TrainNumber", trainNumber);
                modelAndView.addObject("Statement1", "Station " + stationName + " has not been added");
                modelAndView.addObject("Statement2", "Station or stop time already exists for this train");
                log.info("station " + stationName + " already exists for train " + trainNumber);
            }
        }
        else {
            modelAndView.setViewName("adminwronginput");
            log.error("add station for train - wrong input");
        }
        return modelAndView;
    }

    /**
     * Go to "delete station for train" page.
     */
    @RequestMapping(value = "/deletestationfortrain", method = RequestMethod.POST)
    public ModelAndView deleteStationForTrain(@ModelAttribute("train") int trainNumber) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("deletestationfortrain");
        modelAndView.addObject("TrainNumber", trainNumber);
        log.info("delete station for train page visited");
        return modelAndView;
    }

    /**
     * Deletes station from the train route.
     *
     * @param trainNumber train number
     * @param stationName stationName
     */
    @RequestMapping(value = "/finishdeletestationfortrain", method = RequestMethod.POST)
    public ModelAndView finishDeleteStationForTrain(@ModelAttribute("train") int trainNumber, @ModelAttribute("station") String stationName) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("trainaddsuccess");
        if (stationService.isExistForTrainDelete(trainNumber, stationName)) {
            modelAndView.addObject("TrainNumber", trainNumber);
            modelAndView.addObject("Statement1", "Station " + stationName + " has been deleted");
            log.info("station " + stationName + " deleted successfully for train " + trainNumber);
            toTimeBoardMethod(stationName);
        }
        else {
            modelAndView.setViewName("trainaddsuccess");
            modelAndView.addObject("TrainNumber", trainNumber);
            modelAndView.addObject("Statement1", "Station has not been found");
            modelAndView.addObject("Statement2", "Station " + stationName + " does not exist for this train");
            log.info("station " + stationName + " does not exist for train " + trainNumber);
        }
        return modelAndView;
    }

    /**
     * Go to "add station" page.
     */
    @RequestMapping(value = "/addstation", method = RequestMethod.GET)
    public ModelAndView addStation() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("addstation");
        log.info("add station page visited");
        return modelAndView;
    }

    /**
     * Adds station to the database if it does not exist.
     *
     * @param stationName stationName
     */
    @RequestMapping(value = "/finishaddstation", method = RequestMethod.POST)
    public ModelAndView finishAddStation(@ModelAttribute("station") String stationName) {
        ModelAndView modelAndView = new ModelAndView();
        if (isValidInput(stationName)) {
            if (!(stationService.isExist(stationName))) {
                modelAndView.setViewName("stationaddsuccess");
                log.info("station " + stationName + " added successfully");
            }
            else {
                modelAndView.setViewName("stationaddfail");
                log.info("station " + stationName + " already exists");
            }
        }
        else {
            modelAndView.setViewName("adminwronginput");
            log.error("add station - wrong input");
        }
        return modelAndView;
    }

    /**
     * Uploads the list of all trains in the database.
     *
     * @param page page number
     */
    @RequestMapping(value = "/alltrains", method = RequestMethod.POST)
    public ModelAndView allTrains(@ModelAttribute("page") int page) {
        ModelAndView modelAndView = new ModelAndView();
        int trainsCount = trainService.allTrainsCount();
        if (trainsCount == 0) {
            modelAndView.setViewName("notrainsfoundadmin");
            log.info("no trains found in database");
        }
        else {
            int pagesCount = (trainsCount + 6) / 7;
            modelAndView.setViewName("alltrains");
            modelAndView.addObject("AllTrainsList", trainService.allTrains(page));
            modelAndView.addObject("Page", page);
            modelAndView.addObject("PagesCount", pagesCount);
            log.info("all trains page " + page + " visited");
        }
        return modelAndView;
    }

    /**
     * Go to "choose train" page.
     */
    @RequestMapping(value = "/choosetrain", method = RequestMethod.GET)
    public ModelAndView chooseTrain() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("choosetrain");
        log.info("choose train page visited");
        return modelAndView;
    }

    /**
     * Uploads the list of passengers for this train.
     *
     * @param trainNumber train number
     * @param page page number
     */
    @RequestMapping(value = "/passengersbytrain", method = RequestMethod.POST)
    public ModelAndView passengersByTrain(@ModelAttribute("train") int trainNumber, @ModelAttribute("page") int page) {
        ModelAndView modelAndView = new ModelAndView();
        int passengersCount = passengerService.passengersByTrainCount(trainNumber);
        if (passengersCount == 0) {
            modelAndView.setViewName("nopassengersfound");
            log.info("no passengers forund for train " + trainNumber);
        }
        else {
            int pagesCount = (passengersCount + 7) / 8;
            modelAndView.setViewName("passengersbytrain");
            modelAndView.addObject("PassengersList", passengerService.passengersByTrain(trainNumber, page));
            modelAndView.addObject("TrainNumber", trainNumber);
            modelAndView.addObject("Page", page);
            modelAndView.addObject("PagesCount", pagesCount);
            log.info("" + passengersCount + " were found for train " + trainNumber);
        }
        return modelAndView;
    }

    /**
     * Deletes train by train number.
     * Invokes method for notification of timeboard for each
     * station that was on the train's route.
     *
     * @param trainNumber train number
     */
    @RequestMapping(value = "/deletetrain", method = RequestMethod.POST)
    public ModelAndView deleteTrain(@ModelAttribute("train") int trainNumber) {
        ModelAndView modelAndView = new ModelAndView();
        List<String> stations = trainService.deleteTrain(trainNumber);
        for (String stationName : stations) {
            toTimeBoardMethod(stationName);
        }
        modelAndView.setViewName("traindeletesuccess");
        log.info("train " + trainNumber + " deleted successfully");
        return modelAndView;
    }

    /**
     * Redirects to exception.jsp page if any exception caught.
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("exception");
        log.error("exception caught");
        return modelAndView;
    }

    /**
     * Redirects to 403.jsp page if an unauthorized request
     * attempt made.
     */
    @RequestMapping(value = "/403")
    public ModelAndView accessDeniedException() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("403");
        log.error("access denied exception - 403");
        return modelAndView;
    }

    /**
     * Redirects to 404.jsp page if the page has not been found.
     */
    @RequestMapping(value = "/404")
    public ModelAndView resourceNotFoundException() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404");
        log.error("resource not found exception - 404");
        return modelAndView;
    }

    /**
     * Invoked when it is necessary to send notification to the
     * timeboard.
     * Sends info about trains and theirs stops at this station
     * separated by '/' symbols.
     *
     * @param stationName station name
     */
    public void toTimeBoardMethod(String stationName) {
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
        messageSender.sendMessage(toTimeBoardString);
        log.info("message sent to timeboard: " + toTimeBoardString);
    }

    /**
     * Checks if the entered data valid.
     *
     * @param str entered data
     * @return true if the string is not null, not empty and does not
     * contain '/' symbols; false otherwise
     */
    public boolean isValidInput(String str) {
        if (str == null || str.length() == 0 || str.length() > 44)
            return false;
        else {
            for (char c : str.toCharArray())
                if (c == '/')
                    return false;
            return true;
        }
    }

    /**
     * Checks if the entered birthdate valid.
     *
     * @param str entered birthdate
     * @return true if the birthdate is valid; false otherwise
     */
    public boolean isBirthDate(String str) {
        try {
            (new SimpleDateFormat("yyyy-MM-dd")).parse(str);
        }
        catch (ParseException e) {
            return  false;
        }
        return true;
    }

    /**
     * Checks if the entered time valid.
     *
     * @param str entered time
     * @return true if the time is valid; false otherwise
     */
    public boolean isTime(String str) {
        try {
            (new SimpleDateFormat("HH:mm")).parse(str);
        }
        catch (ParseException e) {
            return  false;
        }
        return true;
    }

    /**
     * Favicon mapping to prevent exception.
     */
    @RequestMapping(value = "/favicon.ico", method = RequestMethod.GET)
    public void favicon() { }

}