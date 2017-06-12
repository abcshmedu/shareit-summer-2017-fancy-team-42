package edu.hm.shareit.models;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

/**
 * 
 * @author Thomas Murschall
 *
 */
@Inheritance(strategy = InheritanceType.JOINED)
@MappedSuperclass
public class Medium {
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
