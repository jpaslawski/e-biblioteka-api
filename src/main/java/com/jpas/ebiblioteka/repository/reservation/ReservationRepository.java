package com.jpas.ebiblioteka.repository.reservation;

import com.jpas.ebiblioteka.entity.Book;
import com.jpas.ebiblioteka.entity.Reservation;
import com.jpas.ebiblioteka.entity.User;

import java.util.List;

public interface ReservationRepository {

    List<Reservation> getReservations();

    List<Reservation> getUserReservations(User user);

    Integer getReservationCountForBook(Book book);

    Reservation getReservation(Integer reservationId);

    void addReservation(Reservation reservation);

    void updateReservation(Reservation reservation);

    void deleteReservation(Reservation reservation);
}
