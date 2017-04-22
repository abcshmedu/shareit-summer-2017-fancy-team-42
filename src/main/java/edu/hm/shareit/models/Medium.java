package edu.hm.shareit.models;

/**
 * 
 * @author Thomas Murschall
 *
 */
public class Medium {
    private String title;
    /**
     * 
     * @param title 
     */
    public Medium(String title) {
        this.title = title;
    }
    /**
     * Equals method.
     * @return boolean, is this object equal to other
     * @param other Object to compare with
     */
    //TODO
    public boolean equals(Object other) {
        return false;
    }
    /**
     * Hashfunction.
     * @return int, the hashcode
     */
    //TODO
    public int hashCode() {
        return 0;
    }
    /**
     * ToString representation.
     * @return String representation of this object
     */
    //TODO
    public String toString() {
        return "";
    }
    /**
     * 
     * @return Title of Medium
     */
    public String getTitle() {
        return title;
    }
}
