package com.jpas.ebiblioteka.service.book;

import com.jpas.ebiblioteka.entity.Book;
import com.jpas.ebiblioteka.entity.BookCategory;
import com.jpas.ebiblioteka.entity.request.BookData;
import com.jpas.ebiblioteka.entity.response.BookResponse;

import java.util.List;
import java.util.Set;

public interface BookService {

    List<Book> getBooks();

    List<Book> getBooksByCategory(String category);

    BookResponse getBook(Integer bookId);

    Book getBookById(Integer bookId);

    void saveBook(Book book, Set<String> categories);

    BookResponse updateBook(BookData bookData, Integer bookId);

    Boolean deleteBook(Integer bookId);

    List<BookCategory> getCategories();

    BookCategory getCategoryById(Integer categoryId);

    BookCategory getCategoryByName(String category);

    void saveCategory(BookCategory category);

    Boolean deleteCategory(Integer categoryId);
}
