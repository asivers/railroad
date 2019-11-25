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
public class StationServiceTest {

    @Autowired
    private StationService stationService;

    @Test
    public void stationsByTrainCount0() {
        int trainNumber = 123456;
        int result = stationService.stationsByTrainCount(trainNumber);
        Assert.assertNotEquals(0, result);
    }

    @Test
    public void stationsByTrain0() {
        int page = 1;
        int trainNumber = 123456;
        int result = stationService.stationsByTrain(trainNumber, page).size();
        Assert.assertNotEquals(0, result);
    }

    @Test
    public void isExist0() {
        String stationName = "Kavgolovo";
        boolean result = stationService.isExist(stationName);
        Assert.assertTrue(result);
    }

    @Test
    public void isExistForTrain0() {
        int trainNumber = 123456;
        String stationName = "Spb-Balt";
        String stopTime = "12:00";
        boolean result = stationService.isExistForTrain(trainNumber, stationName, stopTime);
        Assert.assertTrue(result);
    }

    @Test
    public void isExistForTrainDelete0() {
        int trainNumber = 123456;
        String stationName = "Kavgolovo";
        boolean result = stationService.isExistForTrainDelete(trainNumber, stationName);
        Assert.assertFalse(result);
    }

}
