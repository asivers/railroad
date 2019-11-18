package railroad.dao.impl;

import com.fasterxml.classmate.AnnotationConfiguration;
import com.fasterxml.classmate.AnnotationInclusion;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import railroad.config.HibernateConfig;
import railroad.model.Station;
import railroad.model.Train;

import java.lang.annotation.Annotation;

public class TrainDAOImplTest {

    private TrainDAOImpl trainDAOImpl = new TrainDAOImpl();

//    private SessionFactory sessionFactory;
//
//    @Autowired
//    public void setSessionFactory(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }
//
////    private SessionFactory sessionFactory;
////    private Session session = null;
//
//    @Before
//    public void before() {
//    }

    @Test
    public void trainsByStationCount() {
        //given
        String stationName = "Kavgolovo";

        //run
        int result = trainDAOImpl.trainsByStationCount(stationName);
        boolean toCompare = (result == 3);

        //assert
        Assert.assertTrue(toCompare);
    }

//    @After
//    public void after() {
//        session.close();
//        sessionFactory.close();
//    }

}
