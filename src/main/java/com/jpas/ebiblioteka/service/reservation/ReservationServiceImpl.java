package com.jpas.ebiblioteka.service.reservation;

import com.jpas.ebiblioteka.entity.Book;
import com.jpas.ebiblioteka.entity.Reservation;
import com.jpas.ebiblioteka.entity.User;
import com.jpas.ebiblioteka.repository.reservation.ReservationRepository;
import com.jpas.ebiblioteka.service.book.BookService;
import com.jpas.ebiblioteka.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Override
    @Transactional
    public List<Reservation> getReservations() {
        return reservationRepository.getReservations();
    }

    @Override
    @Transactional
    public List<Reservation> getUserReservations(String token) {
        User user = userService.getUserByToken(token);
        if(user != null) {
            return reservationRepository.getUserReservations(user);
        }
        return null;
    }

    @Override
    @Transactional
    public Integer getReservationCountForBook(Integer bookId) {
        Book book = bookService.getBookById(bookId);
        return reservationRepository.getReservationCountForBook(book);
    }

    @Override
    @Transactional
    public Reservation getReservation(Integer reservationId) {
        return reservationRepository.getReservation(reservationId);
    }

    @Override
    @Transactional
    public Reservation addReservation(String token, Integer bookId) throws Exception {
        User user = userService.getUserByToken(token);
        Book book = bookService.getBookById(bookId);

        // Check if this user hasn't already reserved the book
        List<Reservation> reservations = reservationRepository.getUserReservations(user);
        if(user != null && book != null && book.getQuantity() > reservationRepository.getReservationCountForBook(book)) {
            for(Reservation reservation : reservations) {
                if(reservation.getBook().getId().equals(bookId)) {
                    throw new Exception("You already reserved this book!");
                }
            }

            Reservation reservation = new Reservation(user, book);
            user.addReservation(reservation);
            book.addReservation(reservation);
            reservationRepository.addReservation(reservation);

            return reservation;
        }
        return null;
    }

    @Override
    @Transactional
    public Reservation updateReservation(String token, Integer reservationId, String status) {
        Reservation reservation = getReservation(reservationId);
        User user = userService.getUserByToken(token);
        if(reservation != null && reservation.getUser() == user) {
            switch (status) {
                case "COLLECTED":
                    reservation.setStatus(Reservation.ReservationStatus.COLLECTED);
                    break;
                case "RETURNED":
                    reservation.setStatus(Reservation.ReservationStatus.RETURNED);
                    break;
                default:
                    return reservation;
            }
            reservationRepository.updateReservation(reservation);
            return reservation;
        }

        return null;
    }

    @Override
    @Transactional
    public Boolean deleteReservation(Integer reservationId) {
        Reservation reservation = getReservation(reservationId);
        if(reservation != null) {
            reservationRepository.deleteReservation(reservation);
            return true;
        }

        return false;
    }
}
