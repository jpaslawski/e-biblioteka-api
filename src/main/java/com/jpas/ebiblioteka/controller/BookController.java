package com.jpas.ebiblioteka.controller;

import com.jpas.ebiblioteka.entity.response.BookResponse;
import com.jpas.ebiblioteka.entity.response.UserNotification;
import com.jpas.ebiblioteka.service.book.BookService;
import com.jpas.ebiblioteka.entity.Book;
import com.jpas.ebiblioteka.entity.BookCategory;
import com.jpas.ebiblioteka.entity.request.BookData;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getBooks() {
        List<Book> books = bookService.getBooks();
        if(books.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(books);
    }

    @GetMapping("/books/filter")
    public ResponseEntity<List<Book>> getBooksByCategory(@RequestParam(value="category") String category) {
        List<Book> books = bookService.getBooksByCategory(category);
        if(books.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(books);
    }

    @GetMapping("/books/{bookId}")
    public ResponseEntity<BookResponse> getBook(@PathVariable("bookId") Integer bookId) {
        BookResponse book = bookService.getBook(bookId);
        if(book == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return  ResponseEntity.ok(book);
    }

    @GetMapping("/books/notifications")
    public ResponseEntity<?> getUserNotifications(@RequestHeader("Authorization") String header) {
        List<UserNotification> notifications = bookService.getUserNotifications(header);
        if(notifications.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(notifications);
    }

    @PostMapping("/library/books")
    public ResponseEntity<?> saveBook(@RequestBody BookData bookData) {
        Book book = new Book(
                bookData.getName(),
                bookData.getAuthor(),
                bookData.getQuantity(),
                bookData.getImage());
        bookService.saveBook(book, bookData.getCategories());
        return ResponseEntity.ok(book);
    }

    @PutMapping("/library/books/{bookId}")
    public ResponseEntity<?> updateBook(@RequestBody BookData bookData, @PathVariable("bookId") Integer bookId) {
        BookResponse updatedBook = bookService.updateBook(bookData, bookId);
        if(updatedBook == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/library/books/{bookId}")
    public ResponseEntity<JSONObject> deleteBook(@PathVariable("bookId") Integer bookId) {
        Boolean deleted = bookService.deleteBook(bookId);
        JSONObject response = new JSONObject();
        if(deleted) {
            response.put("message", "Książka o identyfikatorze " + bookId + " została pomyślnie usunięta!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.put("message", "Nie znaleziono książki o identyfikatorze " + bookId + "!");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/categories")
    public ResponseEntity<?> getBookCategories() {
        List<BookCategory> bookCategories = bookService.getCategories();
        if(bookCategories.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(bookCategories);
    }

    @PostMapping("/library/categories")
    public ResponseEntity<?> saveCategory(@RequestBody BookCategory category) {
        bookService.saveCategory(category);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/library/categories/{categoryId}")
    public ResponseEntity<JSONObject> deleteCategory(@PathVariable("categoryId") Integer categoryId) {
        Boolean deleted = bookService.deleteCategory(categoryId);
        JSONObject response = new JSONObject();
        if(deleted) {
            response.put("message", "Kategoria o identyfikatorze " + categoryId + " została pomyślnie usunięta!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.put("message", "Nie znaleziono kategorii o identyfikatorze " + categoryId + "!");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
