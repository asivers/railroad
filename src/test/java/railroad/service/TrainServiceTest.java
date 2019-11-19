package railroad.dao.impl;

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
import railroad.service.TrainService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes=
                {
                        AppInitializer.class,
                        HibernateConfig.class,
                        MessagingConfiguration.class,
                        MessagingListenerConfiguration.class,
                        SecurityWebInitializer.class,
                        WebConfig.class,
                        WebSecurityConfig.class
                },
        loader=
                AnnotationConfigWebContextLoader.class)
@WebAppConfiguration
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
public class TrainServiceTest {

    @Autowired
    private TrainService trainService;

    @Test
    public void trainsByStationCount() {
        //given
        String stationName = "Kavgolovo";

        //run
        int result = trainService.trainsByStationCount(stationName);
        boolean toCompare = (result == 3);

        //assert
        Assert.assertTrue(toCompare);
    }

}
