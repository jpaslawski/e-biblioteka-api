package com.jpas.ebiblioteka.service.book;

import com.jpas.ebiblioteka.entity.Book;
import com.jpas.ebiblioteka.entity.BookCategory;
import com.jpas.ebiblioteka.entity.request.BookData;

import java.util.List;
import java.util.Set;

public interface BookService {

    List<Book> getBooks();

    List<Book> getBooksByCategory(String category);

    Book getBookById(Integer bookId);

    void saveBook(Book book, Set<String> categories);

    Book updateBook(BookData bookData, Integer bookId);

    Boolean deleteBook(Integer bookId);

    List<BookCategory> getCategories();

    BookCategory getCategoryById(Integer categoryId);

    BookCategory getCategoryByName(String category);

    void saveCategory(BookCategory category);

    Boolean deleteCategory(Integer categoryId);
}
