package railroad.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import railroad.dao.PassengerDAO;
import railroad.model.Passenger;
import railroad.model.Ticket;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    public List<Passenger> allPassengers() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Passenger").list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean isOnTrain(String firstName, String secondName, String birthDate, int trainNumber) {
        Session session = sessionFactory.getCurrentSession();
        java.sql.Timestamp tzBirthDate = Timestamp.valueOf(birthDate + " 03:00:00.0");
        int isNewPassenger = session.createQuery("SELECT COUNT(*) FROM Passenger AS p WHERE p.first_name = :firstName AND p.second_name = :secondName AND p.birth_date = :tzBirthDate", Number.class).setParameter("firstName", firstName).setParameter("secondName", secondName).setParameter("tzBirthDate", tzBirthDate).getSingleResult().intValue();
        int passengerID = 0;
        int trainID = session.createQuery("SELECT t.id FROM Train AS t WHERE t.number = :trainNumber", Number.class).setParameter("trainNumber", trainNumber).getSingleResult().intValue();
        if (isNewPassenger == 0) {
            Passenger newPassenger = new Passenger();
            newPassenger.setFirstName(firstName);
            newPassenger.setSecondName(secondName);
            newPassenger.setBirthDate(tzBirthDate);
            session.save(newPassenger);
            passengerID = session.createQuery("SELECT p.id FROM Passenger AS p WHERE p.first_name = :firstName AND p.second_name = :secondName AND p.birth_date = :tzBirthDate", Number.class).setParameter("firstName", firstName).setParameter("secondName", secondName).setParameter("tzBirthDate", tzBirthDate).getSingleResult().intValue();
            Ticket newTicket = new Ticket();
            newTicket.setTrainId(trainID);
            newTicket.setPassengerId(passengerID);
            session.save(newTicket);
            return false;
        }
        else {
            passengerID = session.createQuery("SELECT p.id FROM Passenger AS p WHERE p.first_name = :firstName AND p.second_name = :secondName AND p.birth_date = :tzBirthDate", Number.class).setParameter("firstName", firstName).setParameter("secondName", secondName).setParameter("tzBirthDate", tzBirthDate).getSingleResult().intValue();
            int isOnTrain = session.createQuery("SELECT COUNT(*) FROM Ticket AS i WHERE i.passenger_id = :passengerID AND i.train_id = :trainID", Number.class).setParameter("passengerID", passengerID).setParameter("trainID", trainID).getSingleResult().intValue();
            if (isOnTrain == 0) {
                Ticket newTicket = new Ticket();
                newTicket.setTrainId(trainID);
                newTicket.setPassengerId(passengerID);
                session.save(newTicket);
                return false;
            }
            else
                return true;
        }
    }

    @Override
    public void add(Passenger passenger) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(passenger);
    }

    @Override
    public Passenger getById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Passenger.class, id);
    }
}