package com.jpas.ebiblioteka.service.book;

import com.jpas.ebiblioteka.entity.Book;
import com.jpas.ebiblioteka.entity.BookCategory;
import com.jpas.ebiblioteka.entity.Reservation;
import com.jpas.ebiblioteka.entity.User;
import com.jpas.ebiblioteka.entity.request.BookData;
import com.jpas.ebiblioteka.entity.response.BookResponse;
import com.jpas.ebiblioteka.entity.response.UserNotification;
import com.jpas.ebiblioteka.repository.book.BookRepository;
import com.jpas.ebiblioteka.service.reservation.ReservationService;
import com.jpas.ebiblioteka.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReservationService reservationService;

    @Override
    @Transactional
    public List<Book> getBooks() {
        return bookRepository.getBooks();
    }

    @Override
    @Transactional
    public List<Book> getBooksByCategory(String category) {
        BookCategory bookCategory = bookRepository.getCategoryByName(category);
        if(bookCategory != null) {
            return bookRepository.getBooksByCategory(bookCategory);
        }
        return new ArrayList<>();
    }

    @Override
    @Transactional
    public Book getBookById(Integer bookId) {
        return bookRepository.getBookById(bookId);
    }

    @Override
    @Transactional
    public BookResponse getBook(Integer bookId) {
        Book book = bookRepository.getBookById(bookId);
        if(book != null) {
            return new BookResponse(book, book.getQuantity() - reservationService.getReservationCountForBook(bookId));
        }
        return null;
    }

    @Override
    @Transactional
    public List<UserNotification> getUserNotifications(String header) {
        List<Reservation> reservations = reservationService.getUserReservations(header);

        if(!reservations.isEmpty()) {
            Long currentTime = new Date().getTime();
            List<UserNotification> notifications = new ArrayList<>();

            for(Reservation reservation : reservations) {
                long dayDiff = (currentTime - reservation.getDate()) / 86400000;
                if(reservation.getStatus() == Reservation.ReservationStatus.RESERVED && dayDiff > 2) {
                    reservationService.updateReservation(header, reservation.getId(), "RETURNED");
                    notifications.add(new UserNotification(reservation.getBook().getName(), "RETURNED", 0L));
                } else if(reservation.getStatus() == Reservation.ReservationStatus.COLLECTED ) {
                    if(dayDiff > 30) {
                        notifications.add(new UserNotification(reservation.getBook().getName(), "COLLECTED", 0L));
                    } else if(dayDiff > 23) {
                        notifications.add(new UserNotification(reservation.getBook().getName(), "COLLECTED", 30 - dayDiff));
                    }
                }
            }
            return notifications;
        }
        return new ArrayList<>();
    }

    @Override
    @Transactional
    public void saveBook(Book book, Set<String> categories) {
        book.setId(0);
        Set<BookCategory> bookCategorySet = new HashSet<>();
        for(String category : categories) {
            BookCategory bookCategory = bookRepository.getCategoryByName(category);
            bookCategorySet.add(bookCategory);
        }
        book.setCategories(bookCategorySet);
        bookRepository.saveBook(book);
    }

    @Override
    @Transactional
    public BookResponse updateBook(BookData bookData, Integer bookId) {
        Book book  = bookRepository.getBookById(bookId);
        if(book != null) {
            book.setName(bookData.getName());
            book.setAuthor(bookData.getAuthor());
            book.setImage(bookData.getImage());
            book.setQuantity(bookData.getQuantity());
            book.getCategories().clear();

            Set<BookCategory> bookCategorySet = new HashSet<>();
            for(String category : bookData.getCategories()) {
                BookCategory bookCategory = bookRepository.getCategoryByName(category);
                bookCategorySet.add(bookCategory);
            }
            book.setCategories(bookCategorySet);
            bookRepository.updateBook(book);

            return new BookResponse(book, book.getQuantity() - reservationService.getReservationCountForBook(bookId));
        }
        return null;
    }

    @Override
    @Transactional
    public Boolean deleteBook(Integer bookId) {
        Book book = bookRepository.getBookById(bookId);
        if(book != null) {
            bookRepository.deleteBook(book);
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public List<BookCategory> getCategories() {
        return bookRepository.getCategories();
    }

    @Override
    @Transactional
    public BookCategory getCategoryById(Integer categoryId) {
        return bookRepository.getCategoryById(categoryId);
    }

    @Override
    @Transactional
    public BookCategory getCategoryByName(String category) {
        return bookRepository.getCategoryByName(category);
    }

    @Override
    @Transactional
    public void saveCategory(BookCategory category) {
        category.setId(0);
        bookRepository.saveCategory(category);
    }

    @Override
    @Transactional
    public Boolean deleteCategory(Integer categoryId) {
        BookCategory category = bookRepository.getCategoryById(categoryId);
        if(category != null) {
            bookRepository.deleteCategory(category);
            return true;
        }

        return false;
    }
}
