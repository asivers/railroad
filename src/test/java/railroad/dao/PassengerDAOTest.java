package railroad.dao;

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

import java.sql.Timestamp;
import java.util.List;

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
public class PassengerDAOTest {

    @Autowired
    private PassengerDAO passengerDAO;

    @Test
    public void countByTrainNumber0() {
        int trainNumber = 123456;
        int result = passengerDAO.countByTrainNumber(trainNumber);
        Assert.assertNotEquals(0, result);
    }

    @Test
    public void getIdByTrainNumberList0() {
        int page = 1;
        int onPage = 8;
        int trainNumber = 123456;
        int result = passengerDAO.getIdByTrainNumberList(trainNumber, page, onPage).size();
        Assert.assertNotEquals(0, result);
    }

    @Test
    public void getFirstNameByIdSingle0() {
        int id = 1;
        String result = passengerDAO.getFirstNameByIdSingle(id);
        Assert.assertEquals("Harry", result);
    }

    @Test
    public void getSecondNameByIdSingle0() {
        int id = 1;
        String result = passengerDAO.getSecondNameByIdSingle(id);
        Assert.assertEquals("Potter", result);
    }

    @Test
    public void getBirthDateByIdSingle0() {
        int id = 1;
        String result = passengerDAO.getBirthDateByIdSingle(id);
        Assert.assertEquals("1981-01-01", result);
    }

    @Test
    public void countByParameters0() {
        String firstName = "Harry";
        String secondName = "Potter";
        String birthDate = "1981-01-01";
        java.sql.Timestamp tzBirthDate = Timestamp.valueOf(birthDate + " 03:00:00.0");
        int result = passengerDAO.countByParameters(firstName, secondName, tzBirthDate);
        Assert.assertEquals(1, result);
    }

    @Test
    public void getIdByParametersSingle0() {
        String firstName = "Harry";
        String secondName = "Potter";
        String birthDate = "1981-01-01";
        java.sql.Timestamp tzBirthDate = Timestamp.valueOf(birthDate + " 03:00:00.0");
        int result = passengerDAO.getIdByParametersSingle(firstName, secondName, tzBirthDate);
        Assert.assertEquals(1, result);
    }

    @Test
    public void getFirstNameByTicketIdSingle0() {
        int id = 1;
        String result = passengerDAO.getFirstNameByTicketIdSingle(id);
        Assert.assertEquals("Harry", result);
    }

    @Test
    public void getSecondNameByTicketIdSingle0() {
        int id = 1;
        String result = passengerDAO.getSecondNameByTicketIdSingle(id);
        Assert.assertEquals("Potter", result);
    }

    @Test
    public void getBirthDateByTicketIdSingle0() {
        int id = 1;
        String result = passengerDAO.getBirthDateByTicketIdSingle(id);
        Assert.assertEquals("1981-01-01", result);
    }

}
