package railroad.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import railroad.dao.TicketDAO;
import railroad.model.Ticket;

import java.util.List;

@Repository
@Transactional
public class TicketDAOImpl implements TicketDAO {

    private SessionFactory sessionFactory;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Gets number of tickets by user's id.
     *
     * @param user_id user's id
     */
    @Override
    @SuppressWarnings("unchecked")
    public int countByUserId(int user_id) {
        return sessionFactory.getCurrentSession()
                .createQuery("SELECT i.id FROM Ticket AS i INNER JOIN Passenger AS p ON i.passenger_id = p.id INNER JOIN PassengerUser AS pu ON p.id = pu.passenger_id WHERE pu.user_id = :userID")
                .setParameter("userID", user_id).list().size();
    }

    /**
     * Gets number of tickets by passenger's id and train's id.
     * If the passenger is already on the train, the result will be 1.
     *
     * @param passenger_id passenger's id
     * @param train_id train's id
     */
    @Override
    @SuppressWarnings("unchecked")
    public int countByPassengerIdAndTrainId(int passenger_id, int train_id) {
        return sessionFactory.getCurrentSession()
                .createQuery("SELECT i.id FROM Ticket AS i WHERE i.passenger_id = :passengerID AND i.train_id = :trainID")
                .setParameter("passengerID", passenger_id)
                .setParameter("trainID", train_id)
                .list().size();
    }

    /**
     * Gets number of tickets by train's id.
     *
     * @param train_id train's id
     */
    @Override
    @SuppressWarnings("unchecked")
    public int countByTrainId(int train_id) {
        return sessionFactory.getCurrentSession()
                .createQuery("SELECT i.id FROM Train AS t INNER JOIN Ticket AS i ON t.id = i.train_id WHERE t.id = :trainID")
                .setParameter("trainID", train_id).list().size();
    }

    /**
     * Gets ticket's id list by user's id.
     *
     * @param user_id user's id
     * @param page page number
     * @param onPage number of tickets on the page
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Integer> getIdByUserIdList(int user_id, int page, int onPage) {
        return sessionFactory.getCurrentSession()
                .createQuery("SELECT i.id FROM Ticket AS i INNER JOIN Passenger AS p ON i.passenger_id = p.id INNER JOIN PassengerUser AS pu ON p.id = pu.passenger_id WHERE pu.user_id = :userID")
                .setParameter("userID", user_id).setFirstResult(onPage * (page - 1)).setMaxResults(onPage).list();
    }

    /**
     * Adds new ticket to database.
     *
     * @param newTicket new ticket
     */
    @Override
    @SuppressWarnings("unchecked")
    public void add(Ticket newTicket) {
        sessionFactory.getCurrentSession().save(newTicket);
    }

}
