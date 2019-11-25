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
public class TicketDAOTest {

    @Autowired
    private TicketDAO ticketDAO;

    @Test
    public void countByUserId0() {
        int user_id = 2;
        int result = ticketDAO.countByUserId(user_id);
        Assert.assertNotEquals(0, result);
    }

    @Test
    public void countByPassengerIdAndTrainId0() {
        int passenger_id = 1;
        int train_id = 1;
        int result = ticketDAO.countByPassengerIdAndTrainId(passenger_id, train_id);
        Assert.assertNotEquals(0, result);
    }

    @Test
    public void countByTrainId0() {
        int train_id = 1;
        int result = ticketDAO.countByTrainId(train_id);
        Assert.assertNotEquals(0, result);
    }

    @Test
    public void getIdByUserIdList0() {
        int page = 1;
        int onPage = 8;
        int user_id = 2;
        int result = ticketDAO.getIdByUserIdList(user_id, page, onPage).size();
        Assert.assertNotEquals(0, result);
    }

}
