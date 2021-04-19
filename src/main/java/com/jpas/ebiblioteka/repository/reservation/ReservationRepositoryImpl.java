package com.jpas.ebiblioteka.repository.reservation;

import com.jpas.ebiblioteka.entity.Book;
import com.jpas.ebiblioteka.entity.Reservation;
import com.jpas.ebiblioteka.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Reservation> getReservations() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Reservation", Reservation.class).getResultList();
    }

    @Override
    public List<Reservation> getUserReservations(User user) {
        Session session = sessionFactory.getCurrentSession();
        Query<Reservation> query =
                session.createQuery("FROM Reservation WHERE user =: user", Reservation.class);
        query.setParameter("user", user);
        return query.getResultList();
    }

    @Override
    public Integer getReservationCountForBook(Book book) {
        Session session = sessionFactory.getCurrentSession();
        Query<Reservation> query =
                session.createQuery("FROM Reservation WHERE book=:book AND (status = 0 OR status = 1)", Reservation.class);
        query.setParameter("book", book);

        return query.getResultList().size();
    }

    @Override
    public Reservation getReservation(Integer reservationId) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Reservation.class, reservationId);
    }

    @Override
    public void addReservation(Reservation reservation) {
        Session session = sessionFactory.getCurrentSession();
        session.save(reservation);
    }

    @Override
    public void updateReservation(Reservation reservation) {
        Session session = sessionFactory.getCurrentSession();
        session.update(reservation);
    }

    @Override
    public void deleteReservation(Reservation reservation) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(reservation);
    }
}
