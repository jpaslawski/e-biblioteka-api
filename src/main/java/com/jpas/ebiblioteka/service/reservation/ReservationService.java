package com.jpas.ebiblioteka.service.reservation;

import com.jpas.ebiblioteka.entity.Reservation;
import com.jpas.ebiblioteka.entity.User;

import java.util.List;

public interface ReservationService {

    List<Reservation> getReservations();

    List<Reservation> getUserReservations(String token);

    Integer getReservationCountForBook(Integer bookId);

    Reservation getReservation(Integer reservationId);

    Reservation addReservation(String token, Integer bookId) throws Exception;

    Reservation updateReservation(String token, Integer reservationId, String status);

    Boolean deleteReservation(Integer reservationId);
}
