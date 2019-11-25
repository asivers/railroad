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
public class StationDAOTest {

    @Autowired
    private StationDAO stationDAO;

    @Test
    public void countByTrainNumber0() {
        int trainNumber = 123456;
        int result = stationDAO.countByTrainNumber(trainNumber);
        Assert.assertNotEquals(0, result);
    }

    @Test
    public void getIdByTrainNumberList0() {
        int page = 1;
        int onPage = 8;
        int trainNumber = 123456;
        int result = stationDAO.getIdByTrainNumberList(trainNumber, page, onPage).size();
        Assert.assertNotEquals(0, result);
    }

    @Test
    public void getStationNameByTrainNumberList0() {
        int page = 1;
        int onPage = 8;
        int trainNumber = 123456;
        int result = stationDAO.getStationNameByTrainNumberList(trainNumber, page, onPage).size();
        Assert.assertNotEquals(0, result);
    }

    @Test
    public void getIdByStationNameSingle0() {
        String stationName = "Spb-Balt";
        int result = stationDAO.getIdByStationNameSingle(stationName);
        Assert.assertEquals(1, result);
    }

    @Test
    public void getStationNameByIdSingle0() {
        int id = 1;
        String result = stationDAO.getStationNameByIdSingle(id);
        Assert.assertEquals("Spb-Balt", result);
    }

    @Test
    public void countByStationName0() {
        String stationName = "Spb-Balt";
        int result = stationDAO.countByStationName(stationName);
        Assert.assertEquals(1, result);
    }

}
