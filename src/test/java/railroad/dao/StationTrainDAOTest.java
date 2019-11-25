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
import railroad.model.StationTrain;
import railroad.service.impl.TimeSupport;

import java.util.List;

import static railroad.service.impl.TimeSupport.TimeToLong;

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
public class StationTrainDAOTest {

    @Autowired
    private StationTrainDAO stationTrainDAO;

    @Test
    public void countByStationIdAndTrainId0() {
        int station_id = 1;
        int train_id = 1;
        int result = stationTrainDAO.countByStationIdAndTrainId(station_id, train_id);
        Assert.assertEquals(1, result);
    }

    @Test
    public void getTimeByTrainNumberList0() {
        int trainNumber = 123456;
        int result = stationTrainDAO.getStopTimeByTrainNumberList(trainNumber).size();
        Assert.assertNotEquals(0, result);
    }

    @Test
    public void getStopTimeByStationIdAndTrainNumberSingle0() {
        int id = 1;
        int trainNumber = 123456;
        String stopTime = stationTrainDAO.getStopTimeByStationIdAndTrainNumberSingle(id, trainNumber);
        String result = TimeSupport.LongToTime(TimeToLong(stopTime) - 10800000);
        Assert.assertEquals("12:00", result);
    }

    @Test
    public void getStopTimeByTrainIdAndStationNameSingle0() {
        int id = 1;
        String stationName = "Spb-Balt";
        String stopTime = stationTrainDAO.getStopTimeByTrainIdAndStationNameSingle(id, stationName);
        String result = TimeSupport.LongToTime(TimeToLong(stopTime) - 10800000);
        Assert.assertEquals("12:00", result);
    }

}
