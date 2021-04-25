package com.jpas.ebiblioteka.controller;

import com.jpas.ebiblioteka.entity.Reservation;
import com.jpas.ebiblioteka.service.reservation.ReservationService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @GetMapping("/library/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        List<Reservation> reservations = reservationService.getReservations();
        if(reservations != null) {
            return ResponseEntity.ok(reservations);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getUserReservations(@RequestHeader("Authorization") String token) {
        List<Reservation> reservations = reservationService.getUserReservations(token);
        if(reservations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/reservations/{reservationId}")
    public ResponseEntity<Reservation> getReservation(@PathVariable("reservationId") Integer reservationId) {
        Reservation reservation = reservationService.getReservation(reservationId);
        if(reservation != null) {
            return  ResponseEntity.ok(reservation);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/reservations/{bookId}")
    public ResponseEntity<?> saveReservation(@RequestHeader("Authorization") String token, @PathVariable("bookId") Integer bookId) {
        Reservation reservation = null;
        JSONObject response = new JSONObject();
        try {
            reservation = reservationService.addReservation(token, bookId);
        } catch (Exception e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        if(reservation != null) {
            return ResponseEntity.ok(reservation);
        }

        response.put("message", "Dodanie rezerwacji jest niemożliwe! Użytkownik lub Książka nie istnieje!");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/reservations/{reservationId}")
    public ResponseEntity<?> updateReservation(@RequestHeader("Authorization") String token,
                                               @PathVariable("reservationId") Integer reservationId,
                                               @RequestParam("newStatus") String status) {
        Reservation reservation = reservationService.updateReservation(token, reservationId, status);

        if(reservation != null) {
            if(reservation.getStatus() == Reservation.ReservationStatus.RESERVED) {
                JSONObject response = new JSONObject();
                response.put("message", "Status rezerwacji powinien być COLLECTED lub RETURNED!");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            return ResponseEntity.ok(reservation);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/library/reservations/{reservationId}")
    public ResponseEntity<JSONObject> deleteReservation(@PathVariable("reservationId") Integer reservationId) {
        Boolean deleted = reservationService.deleteReservation(reservationId);
        JSONObject response = new JSONObject();
        if(deleted) {
            response.put("message", "Rezerwacja o identyfikatorze " + reservationId + " została pomyślnie usunięta!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.put("message", "Nie znaleziono rezerwacji o identyfukatorze " + reservationId + "!");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
