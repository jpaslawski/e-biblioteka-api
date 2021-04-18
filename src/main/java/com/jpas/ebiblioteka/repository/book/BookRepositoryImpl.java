package com.jpas.ebiblioteka.repository.book;

import com.jpas.ebiblioteka.entity.Book;
import com.jpas.ebiblioteka.entity.BookCategory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Book> getBooks() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Book", Book.class).getResultList();
    }

    @Override
    public List<Book> getBooksByCategory(BookCategory category) {
        Session session = sessionFactory.getCurrentSession();
        Query<Book> query =
                session.createQuery("SELECT b FROM Book b JOIN b.categories c WHERE c.id=:categoryId", Book.class);
        query.setParameter("categoryId", category.getId());
        return query.getResultList();
    }

    @Override
    public Book getBookById(Integer bookId) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Book.class, bookId);
    }

    @Override
    public void saveBook(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.save(book);
    }

    @Override
    public void updateBook(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.update(book);
    }

    @Override
    public void deleteBook(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(book);
    }

    @Override
    public List<BookCategory> getCategories() {
        Session session = sessionFactory.getCurrentSession();
        Query<BookCategory> query =
                session.createQuery("FROM BookCategory", BookCategory.class);
        if(query.getResultList().size() != 0) {
            return query.getResultList();
        }

        return null;
    }

    @Override
    public BookCategory getCategoryById(Integer categoryId) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(BookCategory.class, categoryId);
    }

    @Override
    public BookCategory getCategoryByName(String category) {
        Session session = sessionFactory.getCurrentSession();
        Query<BookCategory> query =
                session.createQuery("FROM BookCategory WHERE name LIKE :category", BookCategory.class);
        query.setParameter("category", category);

        if(query.getResultList().size() != 0) {
            return query.getSingleResult();
        }

        return null;
    }

    @Override
    public void saveCategory(BookCategory category) {
        Session session = sessionFactory.getCurrentSession();
        session.save(category);
    }

    @Override
    public void deleteCategory(BookCategory category) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(category);
    }
}
