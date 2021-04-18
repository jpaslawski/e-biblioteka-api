package com.jpas.ebiblioteka.repository.book;

import com.jpas.ebiblioteka.entity.Book;
import com.jpas.ebiblioteka.entity.BookCategory;

import java.util.List;

public interface BookRepository {

    List<Book> getBooks();

    List<Book> getBooksByCategory(BookCategory category);

    Book getBookById(Integer bookId);

    void saveBook(Book book);

    void updateBook(Book book);

    void deleteBook(Book book);

    List<BookCategory> getCategories();

    BookCategory getCategoryById(Integer categoryId);

    BookCategory getCategoryByName(String category);

    void saveCategory(BookCategory category);

    void deleteCategory(BookCategory category);
}
