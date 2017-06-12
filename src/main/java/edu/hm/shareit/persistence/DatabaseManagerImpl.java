package edu.hm.shareit.persistence;


import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import edu.hm.ShareitServletContextListener;
import edu.hm.shareit.models.Book;
import edu.hm.shareit.models.Disc;

/**
 * Implementation of interface DatabaseManager.
 * @author Thomas Murschall
 *
 */
public class DatabaseManagerImpl implements DatabaseManager {

    private Session entityManager;
    private Transaction tx;

    /**
     * 
     */
    @Inject
    public DatabaseManagerImpl() {
        entityManager = ShareitServletContextListener.getInjectorInstance().getInstance(SessionFactory.class).getCurrentSession();
    }


    @Override
    public void insertBook(Book book) throws DuplicateException {
        try {
            entityManager = ShareitServletContextListener.getInjectorInstance().getInstance(SessionFactory.class).getCurrentSession();
            tx = entityManager.beginTransaction();
            entityManager.persist(book);
            tx.commit();
        } catch (Exception e) {
            if (e.getMessage().equals("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
                throw new DuplicateException("Already inserted");
            } else {
                throw e;
            }
        }
    }

    @Override
    public void insertDisc(Disc disc) throws DuplicateException {
        try {
        entityManager = ShareitServletContextListener.getInjectorInstance().getInstance(SessionFactory.class).getCurrentSession();
        tx = entityManager.beginTransaction();
        entityManager.persist(disc);
        tx.commit();
        } catch (Exception e) {
            if (e.getMessage().equals("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
                throw new DuplicateException("Already inserted");
            } else {
                throw e;
            }
        }
    }

    @Override
    public Book getBook(String isbn) {
        entityManager = ShareitServletContextListener.getInjectorInstance().getInstance(SessionFactory.class).getCurrentSession();
        tx = entityManager.beginTransaction();
        Book out = entityManager.get(Book.class, isbn);
        tx.commit();
        return out;
    }

    @Override
    public Disc getDisc(String barcode) {
        entityManager = ShareitServletContextListener.getInjectorInstance().getInstance(SessionFactory.class).getCurrentSession();
        tx = entityManager.beginTransaction();
        Disc out = entityManager.get(Disc.class, barcode);
        tx.commit();
        return out;
    }

    @Override
    public void deleteBook(Book book) {
        entityManager = ShareitServletContextListener.getInjectorInstance().getInstance(SessionFactory.class).getCurrentSession();
        tx = entityManager.beginTransaction();
        entityManager.delete(book);
        tx.commit();
    }

    @Override
    public void deleteDisc(Disc disc) {
        entityManager = ShareitServletContextListener.getInjectorInstance().getInstance(SessionFactory.class).getCurrentSession();
        tx = entityManager.beginTransaction();
        entityManager.delete(disc);
        tx.commit();
    }
    
    @Override
    public void deleteAll() {
        List<Book> books = this.getAllBooks();
        List<Disc> discs = this.getAllDiscs();
        if (!books.isEmpty()) {
            for (Iterator<Book> it = books.iterator(); it.hasNext();) {
                this.deleteBook(it.next());
            }
        }
        if (!discs.isEmpty()) {
            for (Iterator<Disc> it = discs.iterator(); it.hasNext();) {
                this.deleteDisc(it.next());
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Book> getAllBooks() {
        entityManager = ShareitServletContextListener.getInjectorInstance().getInstance(SessionFactory.class).getCurrentSession();
        tx = entityManager.beginTransaction();
        List<Book> out = entityManager.createQuery("From Book").list();
        tx.commit();
        return out;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Disc> getAllDiscs() {
        entityManager = ShareitServletContextListener.getInjectorInstance().getInstance(SessionFactory.class).getCurrentSession();
        tx = entityManager.beginTransaction();
        List<Disc> out = entityManager.createQuery("From Disc").list();
        tx.commit();
        return out;
    }

    @Override
    public void updateBook(Book book) throws MediaNotFoundException {
        try {
        entityManager = ShareitServletContextListener.getInjectorInstance().getInstance(SessionFactory.class).getCurrentSession();
        tx = entityManager.beginTransaction();
        entityManager.update(book);
        tx.commit();
    } catch (Exception e) {
        if (e.getMessage().equals("Batch update returned unexpected row count from update [0]; actual row count: 0; expected: 1")) {
            throw new MediaNotFoundException("Media not in Database");
        } else {
            throw e;
        }
    }
    }


    @Override
    public void updateDisc(Disc disc) throws MediaNotFoundException {
        try {
        entityManager = ShareitServletContextListener.getInjectorInstance().getInstance(SessionFactory.class).getCurrentSession();
        tx = entityManager.beginTransaction();
        entityManager.update(disc);
        tx.commit();
        } catch (Exception e) {
            if (e.getMessage().equals("Batch update returned unexpected row count from update [0]; actual row count: 0; expected: 1")) {
                throw new MediaNotFoundException("Media not in Database");
            } else {
                throw e;
            }
        }
    }
    
    /**
     * 
     * @author Thomas Murschall
     *
     */
    public class DuplicateException extends Exception {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        /**
         * 
         * @param message 
         */
        DuplicateException(String message) {
            super(message);
        }
    }
    
    /**
     * 
     * @author Thomas Murschall
     *
     */
    public class MediaNotFoundException extends Exception {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        /**
         * 
         * @param message 
         */
        MediaNotFoundException(String message) {
            super(message);
        }
    }
}
