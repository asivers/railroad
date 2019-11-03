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
    public int passengersByTrainCount(int trainNumber) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT p.id FROM Passenger AS p INNER JOIN Ticket AS i ON p.id = i.passenger_id INNER JOIN Train AS t ON i.train_id = t.id WHERE t.number = :trainNumber", Number.class).setParameter("trainNumber", trainNumber).list().size();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<PassengerInfo> passengersByTrain(int trainNumber, int page) {
        Session session = sessionFactory.getCurrentSession();
        int onPage = 8;
        List<Integer> passengerIDs = session.createQuery("SELECT p.id FROM Passenger AS p INNER JOIN Ticket AS i ON p.id = i.passenger_id INNER JOIN Train AS t ON i.train_id = t.id WHERE t.number = :trainNumber").setParameter("trainNumber", trainNumber).setFirstResult(onPage * (page - 1)).setMaxResults(onPage).list();
        List<PassengerInfo> passengerInfos = new ArrayList<>();
        String passengerFirstName = "";
        String passengerSecondName = "";
        String passengerBirthDate = "";
        for (Integer id : passengerIDs) {
            passengerFirstName = session.createQuery("SELECT p.first_name FROM Passenger AS p WHERE p.id = :id", String.class).setParameter("id", id).getSingleResult();
            passengerSecondName = session.createQuery("SELECT p.second_name FROM Passenger AS p WHERE p.id = :id", String.class).setParameter("id", id).getSingleResult();
            passengerBirthDate = session.createQuery("SELECT p.birth_date FROM Passenger AS p WHERE p.id = :id").setParameter("id", id).getSingleResult().toString().substring(0, 10);
            passengerInfos.add(new PassengerInfo(passengerFirstName, passengerSecondName, passengerBirthDate));
        }
        return passengerInfos;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean isOnTrain(String firstName, String secondName, String birthDate, int trainNumber, int currentUserID) {
        Session session = sessionFactory.getCurrentSession();
        java.sql.Timestamp tzBirthDate = Timestamp.valueOf(birthDate + " 03:00:00.0");
        int isNewPassenger = session.createQuery("SELECT p.id FROM Passenger AS p WHERE p.first_name = :firstName AND p.second_name = :secondName AND p.birth_date = :tzBirthDate", Number.class).setParameter("firstName", firstName).setParameter("secondName", secondName).setParameter("tzBirthDate", tzBirthDate).list().size();
        int passengerID = 0;
        int trainID = session.createQuery("SELECT t.id FROM Train AS t WHERE t.number = :trainNumber", Number.class).setParameter("trainNumber", trainNumber).getSingleResult().intValue();
        if (isNewPassenger == 0) {
            add(firstName, secondName, birthDate);
            passengerID = session.createQuery("SELECT p.id FROM Passenger AS p WHERE p.first_name = :firstName AND p.second_name = :secondName AND p.birth_date = :tzBirthDate", Number.class).setParameter("firstName", firstName).setParameter("secondName", secondName).setParameter("tzBirthDate", tzBirthDate).getSingleResult().intValue();
            PassengerUser newPassengerUser = new PassengerUser();
            newPassengerUser.setPassenger_id(passengerID);
            newPassengerUser.setUser_id(currentUserID);
            session.save(newPassengerUser);
            Ticket newTicket = new Ticket();
            newTicket.setTrainId(trainID);
            newTicket.setPassengerId(passengerID);
            session.save(newTicket);
            return false;
        }
        else {
            passengerID = session.createQuery("SELECT p.id FROM Passenger AS p WHERE p.first_name = :firstName AND p.second_name = :secondName AND p.birth_date = :tzBirthDate", Number.class).setParameter("firstName", firstName).setParameter("secondName", secondName).setParameter("tzBirthDate", tzBirthDate).getSingleResult().intValue();
            int passengerUserRelation = session.createQuery("SELECT pu.user_id FROM PassengerUser AS pu WHERE pu.passenger_id = :passengerID AND pu.user_id = :currentUserID", Number.class).setParameter("passengerID", passengerID).setParameter("currentUserID", currentUserID).list().size();
            if (passengerUserRelation == 1) {
                int isOnTrain = session.createQuery("SELECT i.id FROM Ticket AS i WHERE i.passenger_id = :passengerID AND i.train_id = :trainID", Number.class).setParameter("passengerID", passengerID).setParameter("trainID", trainID).list().size();
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
            else
                return true;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void add(String firstName, String secondName, String birthDate) {
        Session session = sessionFactory.getCurrentSession();
        java.sql.Timestamp tzBirthDate = Timestamp.valueOf(birthDate + " 03:00:00.0");
        Passenger newPassenger = new Passenger();
        newPassenger.setFirstName(firstName);
        newPassenger.setSecondName(secondName);
        newPassenger.setBirthDate(tzBirthDate);
        session.save(newPassenger);
    }

}