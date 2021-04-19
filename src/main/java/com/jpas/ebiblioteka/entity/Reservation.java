package com.jpas.ebiblioteka.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reservation")
public class Reservation {

    public enum ReservationStatus {
        RESERVED, COLLECTED, RETURNED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Integer id;

    @Column(name = "reservation_date")
    private Long date;

    @Column(name = "reservation_status")
    private ReservationStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="reservation_user")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="reservation_book", referencedColumnName = "book_id")
    private Book book;

    public Reservation() {
    }

    public Reservation(User user, Book book) {
        this.user = user;
        this.book = book;
        this.date = new Date().getTime();
        this.status = ReservationStatus.RESERVED;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation )) return false;
        return id != null && id.equals(((Reservation) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
