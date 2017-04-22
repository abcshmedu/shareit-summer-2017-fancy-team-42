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
    //TODO
    @Override
    public boolean equals(Object other) {
        return false;
    }
    //TODO
    @Override
    public int hashCode() {
        return 0;
    }
    //TODO
    @Override
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
