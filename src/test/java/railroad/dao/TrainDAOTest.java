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
import org.springframework.transaction.annotation.Transactional;
import railroad.config.*;
import railroad.service.impl.TimeSupport;

import java.sql.Time;

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
public class TrainDAOTest {

    @Autowired
    private TrainDAO trainDAO;

    @Test
    public void countAllTrains0() {
        int result = trainDAO.countAllTrains();
        Assert.assertNotEquals(0, result);
    }

    @Test
    public void getTrainNumberList0() {
        int page = 1;
        int onPage = 7;
        int result = trainDAO.getTrainNumberList(page, onPage).size();
        Assert.assertNotEquals(0, result);
    }

    @Test
    public void countByStationName0() {
        String stationName = "Kavgolovo";
        int result = trainDAO.countByStationName(stationName);
        Assert.assertNotEquals(0, result);
    }

    @Test
    public void getIdByStationNameList0() {
        int page = 1;
        int onPage = 7;
        String stationName = "Kavgolovo";
        int result = trainDAO.getIdByStationNameList(stationName, page, onPage).size();
        Assert.assertNotEquals(0, result);
    }

    @Test
    public void getTrainNumberByIdSingle0() {
        int id = 1;
        int result = trainDAO.getTrainNumberByIdSingle(id);
        Assert.assertEquals(123456, result);
    }

    @Test
    public void getIdByArrivalSearchList0() {
        String arrivalStationName = "Novy Peterhof";
        Time lowerTime = new Time(TimeSupport.TimeToLong("00:00"));
        Time upperTime = new Time(TimeSupport.TimeToLong("23:59"));
        int result = trainDAO.getIdByArrivalSearchList(arrivalStationName, lowerTime, upperTime).size();
        Assert.assertNotEquals(0, result);
    }

    @Test
    public void isAppropriateBySearch0() {
        int trainID = 1;
        String departureStationName = "Dachnoe";
        Time untilTime = new Time(TimeSupport.TimeToLong("23:59"));
        int result = trainDAO.isAppropriateBySearch(trainID, departureStationName, untilTime);
        Assert.assertNotEquals(0, result);
    }

    @Test
    public void getIdBySearchList0() {
        int page = 1;
        int onPage = 7;
        String departureStationName = "Dachnoe";
        String arrivalStationName = "Novy Peterhof";
        Time lowerTime = new Time(TimeSupport.TimeToLong("00:00"));
        Time upperTime = new Time(TimeSupport.TimeToLong("23:59"));
        int result = trainDAO.getIdBySearchList(departureStationName, arrivalStationName, lowerTime, upperTime, page, onPage).size();
        Assert.assertNotEquals(0, result);
    }

    @Test
    public void getSeatsByIdSingle0() {
        int id = 1;
        int result = trainDAO.getSeatsByIdSingle(id);
        Assert.assertEquals(3, result);
    }

    @Test
    public void countByTrainNumber0() {
        int trainNumber = 123456;
        int result = trainDAO.countByTrainNumber(trainNumber);
        Assert.assertNotEquals(0, result);
    }

    @Test
    public void getIdByTrainNumberSingle0() {
        int trainNumber = 123456;
        int result = trainDAO.getIdByTrainNumberSingle(trainNumber);
        Assert.assertEquals(1, result);
    }

    @Test
    public void getTrainNumberByTicketIdSingle0() {
        int id = 1;
        int result = trainDAO.getTrainNumberByTicketIdSingle(id);
        Assert.assertEquals(123456, result);
    }

}
