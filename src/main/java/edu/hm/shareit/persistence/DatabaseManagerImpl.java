package edu.hm.shareit.persistence;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;

/**
 * Implementation of interface DatabaseManager.
 * @author Thomas Murschall
 *
 */
public class DatabaseManagerImpl implements DatabaseManager {


    private final SessionFactory sessionF;

    private Session entityManager;
    private Transaction tx;

    /**
     * 
     */
    public DatabaseManagerImpl() {
        sessionF = new Configuration().configure().buildSessionFactory();
    }


    @Override
    public void insertBook(Book book) {
        entityManager = sessionF.getCurrentSession();
        tx = entityManager.beginTransaction();
        entityManager.persist(book);
        tx.commit();
    }

    @Override
    public void insertDisc(Disc disc) {
        entityManager = sessionF.getCurrentSession();
        tx = entityManager.beginTransaction();
        entityManager.persist(disc);
        tx.commit();
    }

    @Override
    public Book getBook(String isbn) {
        entityManager = sessionF.getCurrentSession();
        tx = entityManager.beginTransaction();
        Book out = entityManager.get(Book.class, isbn);
        tx.commit();
        return out;
    }

    @Override
    public Disc getDisc(String barcode) {
        entityManager = sessionF.getCurrentSession();
        tx = entityManager.beginTransaction();
        Disc out = entityManager.get(Disc.class, barcode);
        tx.commit();
        return out;
    }

    @Override
    public void deleteBook(Book book) {
        entityManager = sessionF.getCurrentSession();
        tx = entityManager.beginTransaction();
        entityManager.delete(book);
        tx.commit();
    }

    @Override
    public void deleteDisc(Disc disc) {
        entityManager = sessionF.getCurrentSession();
        tx = entityManager.beginTransaction();
        entityManager.delete(disc);
        tx.commit();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Book> getAllBooks() {
        entityManager = sessionF.getCurrentSession();
        tx = entityManager.beginTransaction();
        List<Book> out = entityManager.createQuery("From Book").list();
        tx.commit();
        return out;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Disc> getAllDiscs() {
        entityManager = sessionF.getCurrentSession();
        tx = entityManager.beginTransaction();
        List<Disc> out = entityManager.createQuery("From Disc").list();
        tx.commit();
        return out;
    }

    @Override
    public void updateBook(Book book) {
        entityManager = sessionF.getCurrentSession();
        tx = entityManager.beginTransaction();
        entityManager.update(book);
        tx.commit();
    }


    @Override
    public void updateDisc(Disc disc) {
        entityManager = sessionF.getCurrentSession();
        tx = entityManager.beginTransaction();
        entityManager.update(disc);
        tx.commit();
    }

}
