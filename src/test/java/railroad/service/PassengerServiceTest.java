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
public class PassengerServiceTest {

    @Autowired
    private PassengerService passengerService;

    @Test
    public void passengersByTrainCount0() {
        int trainNumber = 123456;
        int result = passengerService.passengersByTrainCount(trainNumber);
        Assert.assertNotEquals(0, result);
    }

    @Test
    public void passengersByTrain0() {
        int page = 1;
        int trainNumber = 123456;
        int result = passengerService.passengersByTrain(trainNumber, page).size();
        Assert.assertNotEquals(0, result);
    }

    @Test
    public void isOnTrain0() {
        String firstName = "Harry";
        String secondName = "Potter";
        String birthDate = "1981-01-01";
        int trainNumber = 123456;
        int currentUserID = 2;
        boolean result = passengerService.isOnTrain(firstName, secondName, birthDate, trainNumber, currentUserID);
        Assert.assertTrue(result);
    }

}
