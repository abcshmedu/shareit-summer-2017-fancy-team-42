package edu.hm.shareit.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * 
 * @author Thomas Murschall
 *
 */
@Entity 
@Table(name = "TMedium")
@Inheritance(strategy = InheritanceType.JOINED)
public class Medium {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private long id;
    private String title;
    /**
     * 
     * @param title 
     */
    public Medium(String title) {
        this.title = title;
    }
    //TODO
    @Override
    public boolean equals(Object other) {
        Medium m = null;
        try {
            m = (Medium) other;
        }
        catch (Exception e) {
            return false;
        }
        return this.getTitle().equals(m.getTitle());
    }
    //TODO
    @Override
    public int hashCode() {
        return title.hashCode();
    }
    //TODO
    @Override
    public String toString() {
        return "Medium mit dem Titel: " + this.getTitle();
    }
    /**
     * 
     * @return Title of Medium
     */
    public String getTitle() {
        return title;
    }


}
