package com.jpas.ebiblioteka.entity.response;

import com.jpas.ebiblioteka.entity.Book;

public class BookResponse {

    private Book book;

    private Integer availableCopies;

    public BookResponse() {
    }

    public BookResponse(Book book, Integer availableCopies) {
        this.book = book;
        this.availableCopies = availableCopies;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(Integer availableCopies) {
        this.availableCopies = availableCopies;
    }
}
