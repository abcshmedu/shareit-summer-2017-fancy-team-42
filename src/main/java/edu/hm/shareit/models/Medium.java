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
