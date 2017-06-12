package edu.hm.shareit.persistence;


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
        entityManager = sessionF.getCurrentSession();

    }
    
    
    @Override
    public void insertBook(Book book) {
        tx = entityManager.beginTransaction();
        entityManager.persist(book);
        tx.commit();
    }

    @Override
    public void insertDisc(Disc disc) {
        entityManager.persist(disc);
        
        String queryString = "From Book";
        entityManager.createQuery(queryString).list();
    }
    
    @Override
    public Book getBook(String isbn) {
        tx = entityManager.beginTransaction();
        Book out = entityManager.get(Book.class, isbn);
        tx.commit();
        return out;
    }
    
    @Override
    public Disc getDisc(String barcode) {
        return entityManager.get(Disc.class, "Barcode");
    }
}
