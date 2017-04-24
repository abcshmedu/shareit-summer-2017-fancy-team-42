package edu.hm.shareit.models;

/**
 * @author Thomas Murschall
 */
public class Disc extends Medium {
    private String barcode;
    private String director;
    private int fsk;
    
    /**
     * Empty constructor.
     */
    public Disc() {
        super("");
    }
    /**
     * Constructor.
     * 
     * @param barcode 
     * @param director 
     * @param fsk 
     * @param title 
     */
    public Disc(String barcode, String director, int fsk, String title) {
        super(title);
        this.barcode = barcode;
        this.director = director;
        this.fsk = fsk;
    } 
    
    /**
     * 
     * @return String, director
     */
    public String getDirector() {
        return director;
    }
    /**
     * 
     * @return String, barcode
     */
    public String getBarcode() {
        return barcode;
    }
    /**
     * 
     * @return int, fsk
     */
    public int getFsk() {
        return fsk;
    }

    public boolean equals(Object other) {
        Disc d = null;
        try {
            d = (Disc) other;
        }
        catch (Exception e) {
            return false;
        }
        return this.getBarcode().equals(d.getBarcode());
    }

    public int hashCode() {
        return this.getTitle().hashCode() + this.getBarcode().hashCode() + this.getDirector().hashCode() + this.getFsk();
    }

}
