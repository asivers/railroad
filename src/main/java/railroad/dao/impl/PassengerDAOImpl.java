package railroad.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import railroad.dao.PassengerDAO;
import railroad.model.Passenger;
import railroad.model.PassengerUser;
import railroad.model.Ticket;
import railroad.model.additional.PassengerInfo;

import java.sql.Timestamp;
import java.util.*;

@Repository
public class PassengerDAOImpl implements PassengerDAO {

    private SessionFactory sessionFactory;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public int countByTrainNumber(int trainNumber) {
        return sessionFactory.getCurrentSession().createQuery("SELECT p.id FROM Passenger AS p INNER JOIN Ticket AS i ON p.id = i.passenger_id INNER JOIN Train AS t ON i.train_id = t.id WHERE t.number = :trainNumber", Number.class).setParameter("trainNumber", trainNumber).list().size();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Integer> getIdByTrainNumberList(int trainNumber, int page, int onPage) {
        return sessionFactory.getCurrentSession().createQuery("SELECT p.id FROM Passenger AS p INNER JOIN Ticket AS i ON p.id = i.passenger_id INNER JOIN Train AS t ON i.train_id = t.id WHERE t.number = :trainNumber").setParameter("trainNumber", trainNumber).setFirstResult(onPage * (page - 1)).setMaxResults(onPage).list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public String getFirstNameByIdSingle(int id) {
        return sessionFactory.getCurrentSession().createQuery("SELECT p.first_name FROM Passenger AS p WHERE p.id = :id").setParameter("id", id).getSingleResult().toString();
    }

    @Override
    @SuppressWarnings("unchecked")
    public String getSecondNameByIdSingle(int id) {
        return sessionFactory.getCurrentSession().createQuery("SELECT p.second_name FROM Passenger AS p WHERE p.id = :id").setParameter("id", id).getSingleResult().toString();
    }

    @Override
    @SuppressWarnings("unchecked")
    public String getBirthDateByIdSingle(int id) {
        return sessionFactory.getCurrentSession().createQuery("SELECT p.birth_date FROM Passenger AS p WHERE p.id = :id").setParameter("id", id).getSingleResult().toString().substring(0, 10);
    }

    @Override
    @SuppressWarnings("unchecked")
    public int countByParameters(String firstName, String secondName, java.sql.Timestamp tzBirthDate) {
        return sessionFactory.getCurrentSession().createQuery("SELECT p.id FROM Passenger AS p WHERE p.first_name = :firstName AND p.second_name = :secondName AND p.birth_date = :tzBirthDate", Number.class).setParameter("firstName", firstName).setParameter("secondName", secondName).setParameter("tzBirthDate", tzBirthDate).list().size();
    }

    @Override
    @SuppressWarnings("unchecked")
    public int getIdByParametersSingle(String firstName, String secondName, java.sql.Timestamp tzBirthDate) {
        return sessionFactory.getCurrentSession().createQuery("SELECT p.id FROM Passenger AS p WHERE p.first_name = :firstName AND p.second_name = :secondName AND p.birth_date = :tzBirthDate", Number.class).setParameter("firstName", firstName).setParameter("secondName", secondName).setParameter("tzBirthDate", tzBirthDate).getSingleResult().intValue();
    }

    @Override
    @SuppressWarnings("unchecked")
    public String getFirstNameByTicketIdSingle(int id) {
        return sessionFactory.getCurrentSession().createQuery("SELECT p.first_name FROM Ticket AS i INNER JOIN Passenger AS p ON i.passenger_id = p.id WHERE i.id = :id").setParameter("id", id).getSingleResult().toString();
    }

    @Override
    @SuppressWarnings("unchecked")
    public String getSecondNameByTicketIdSingle(int id) {
        return sessionFactory.getCurrentSession().createQuery("SELECT p.second_name FROM Ticket AS i INNER JOIN Passenger AS p ON i.passenger_id = p.id WHERE i.id = :id").setParameter("id", id).getSingleResult().toString();
    }

    @Override
    @SuppressWarnings("unchecked")
    public String getBirthDateByTicketIdSingle(int id) {
        return sessionFactory.getCurrentSession().createQuery("SELECT p.birth_date FROM Ticket AS i INNER JOIN Passenger AS p ON i.passenger_id = p.id WHERE i.id = :id").setParameter("id", id).getSingleResult().toString().substring(0, 10);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void add(Passenger newPassenger) {
        sessionFactory.getCurrentSession().save(newPassenger);
    }

}