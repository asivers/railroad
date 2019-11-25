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
public class TicketServiceTest {

    @Autowired
    private TicketService ticketService;

    @Test
    public void ticketsByUserCount0() {
        int userID = 2;
        int result = ticketService.ticketsByUserCount(userID);
        Assert.assertNotEquals(0, result);
    }

    @Test
    public void ticketsByUser0() {
        int page = 1;
        int userID = 2;
        int result = ticketService.ticketsByUser(userID, page).size();
        Assert.assertNotEquals(0, result);
    }

}
