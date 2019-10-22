package railroad.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import railroad.dao.TicketDAO;
import railroad.model.Ticket;
import java.util.*;

@Repository
public class TicketDAOImpl implements TicketDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Ticket> allTickets() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Ticket").list();
    }

    @Override
    public void add(Ticket ticket) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(ticket);
    }

    @Override
    public Ticket getById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Ticket.class, id);
    }
}