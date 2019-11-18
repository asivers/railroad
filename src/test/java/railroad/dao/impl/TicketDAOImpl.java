package railroad.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import railroad.dao.TicketDAO;
import railroad.model.additional.TicketInfo;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TicketDAOImpl implements TicketDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public int ticketsByUserCount(int userID) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT i.id FROM Ticket AS i INNER JOIN Passenger AS p ON i.passenger_id = p.id INNER JOIN PassengerUser AS pu ON p.id = pu.passenger_id WHERE pu.user_id = :userID", Number.class).setParameter("userID", userID).list().size();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TicketInfo> ticketsByUser(int userID, int page) {
        Session session = sessionFactory.getCurrentSession();
        int onPage = 8;
        List<Integer> ticketIDs = session.createQuery("SELECT i.id FROM Ticket AS i INNER JOIN Passenger AS p ON i.passenger_id = p.id INNER JOIN PassengerUser AS pu ON p.id = pu.passenger_id WHERE pu.user_id = :userID").setParameter("userID", userID).setFirstResult(onPage * (page - 1)).setMaxResults(onPage).list();
        List<TicketInfo> ticketInfos = new ArrayList<>();
        String passengerFirstName = "";
        String passengerSecondName = "";
        String passengerBirthDate = "";
        int trainNumber = 0;
        for (Integer id : ticketIDs) {
            passengerFirstName = session.createQuery("SELECT p.first_name FROM Ticket AS i INNER JOIN Passenger AS p ON i.passenger_id = p.id WHERE i.id = :id").setParameter("id", id).getSingleResult().toString();
            passengerSecondName = session.createQuery("SELECT p.second_name FROM Ticket AS i INNER JOIN Passenger AS p ON i.passenger_id = p.id WHERE i.id = :id").setParameter("id", id).getSingleResult().toString();
            passengerBirthDate = session.createQuery("SELECT p.birth_date FROM Ticket AS i INNER JOIN Passenger AS p ON i.passenger_id = p.id WHERE i.id = :id").setParameter("id", id).getSingleResult().toString().substring(0, 10);
            trainNumber = session.createQuery("SELECT t.number FROM Ticket AS i INNER JOIN Train AS t ON i.train_id = t.id WHERE i.id = :id", Number.class).setParameter("id", id).getSingleResult().intValue();
            ticketInfos.add(new TicketInfo(id, passengerFirstName, passengerSecondName, passengerBirthDate, trainNumber));
        }
        return ticketInfos;
    }

}
