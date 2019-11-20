package railroad.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import railroad.dao.TicketDAO;
import railroad.model.Ticket;

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
    public int countByUserId(int user_id) {
        return sessionFactory.getCurrentSession().createQuery("SELECT i.id FROM Ticket AS i INNER JOIN Passenger AS p ON i.passenger_id = p.id INNER JOIN PassengerUser AS pu ON p.id = pu.passenger_id WHERE pu.user_id = :userID", Number.class).setParameter("userID", user_id).list().size();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Integer> getIdByUserIdList(int userID, int page, int onPage) {
        return sessionFactory.getCurrentSession().createQuery("SELECT i.id FROM Ticket AS i INNER JOIN Passenger AS p ON i.passenger_id = p.id INNER JOIN PassengerUser AS pu ON p.id = pu.passenger_id WHERE pu.user_id = :userID").setParameter("userID", userID).setFirstResult(onPage * (page - 1)).setMaxResults(onPage).list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public int countByPassengerIdAndTrainId(int passenger_id, int train_id) {
        return sessionFactory.getCurrentSession().createQuery("SELECT i.id FROM Ticket AS i WHERE i.passenger_id = :passengerID AND i.train_id = :trainID", Number.class).setParameter("passengerID", passenger_id).setParameter("trainID", train_id).list().size();
    }

    @Override
    @SuppressWarnings("unchecked")
    public int countByTrainId(int train_id) {
        return sessionFactory.getCurrentSession().createQuery("SELECT i.id FROM Train AS t INNER JOIN Ticket AS i ON t.id = i.train_id WHERE t.id = :trainID", Number.class).setParameter("trainID", train_id).list().size();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void add(Ticket newTicket) {
        sessionFactory.getCurrentSession().save(newTicket);
    }

}
