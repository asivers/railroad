package railroad.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import railroad.config.*;
import railroad.service.impl.TimeSupport;

import java.sql.Time;
import java.sql.Timestamp;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes=
                {
                        AppInitializer.class,
                        CustomSpringConfigurator.class,
                        HibernateConfig.class,
                        MessagingConfiguration.class,
                        SecurityWebInitializer.class,
                        WebConfig.class,
                        WebSecurityConfig.class,
                        WebSocketConfigurator.class
                },
        loader=
                AnnotationConfigWebContextLoader.class)
@WebAppConfiguration
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
public class TrainServiceTest {

    @Autowired
    private TrainService trainService;

    @Test
    public void allTrainsCount0() {
        int result = trainService.allTrainsCount();
        Assert.assertNotEquals(0, result);
    }

    @Test
    public void allTrains0() {
        int page = 1;
        int result = trainService.allTrains(page).size();
        Assert.assertNotEquals(0, result);
    }

    @Test
    public void trainsByStationCount0() {
        String stationName = "Kavgolovo";
        int result = trainService.trainsByStationCount(stationName);
        Assert.assertNotEquals(0, result);
    }

    @Test
    public void trainsByStation0() {
        int page = 1;
        String stationName = "Kavgolovo";
        int result = trainService.trainsByStation(stationName, page).size();
        Assert.assertNotEquals(0, result);
    }

    @Test
    public void trainsBySearchCount0() {
        String departureStationName = "Dachnoe";
        String arrivalStationName = "Novy Peterhof";
        String lowerTimeString = "00:00";
        String upperTimeString = "23:59";
        int result = trainService.trainsBySearchCount(departureStationName, arrivalStationName, lowerTimeString, upperTimeString);
        Assert.assertNotEquals(0, result);
    }

    @Test
    public void trainsBySearch0() {
        int page = 1;
        String departureStationName = "Dachnoe";
        String arrivalStationName = "Novy Peterhof";
        String lowerTimeString = "00:00";
        String upperTimeString = "23:59";
        int result = trainService.trainsBySearch(departureStationName, arrivalStationName, lowerTimeString, upperTimeString, page).size();
        Assert.assertNotEquals(0, result);
    }

    @Test
    public void freeSeats0() {
        int trainNumber = 234567;
        boolean result = trainService.freeSeats(trainNumber);
        Assert.assertTrue(result);
    }

    @Test
    public void isExist() {
        int trainNumber = 123456;
        int seats = 3;
        boolean result = trainService.isExist(trainNumber, seats);
        Assert.assertTrue(result);
    }

}
