package com.jpas.ebiblioteka.entity.response;

public class UserNotification {
    private String bookName;

    private String status;

    private Long timeLeft;

    public UserNotification(String bookName, String status, Long timeLeft) {
        this.bookName = bookName;
        this.status = status;
        this.timeLeft = timeLeft;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(Long timeLeft) {
        this.timeLeft = timeLeft;
    }
}
