package edu.hm.shareit.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Thomas Murschall
 */
@Entity 
@Table(name = "TDisc")
public class Disc extends Medium {
    @Id private String barcode;
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
    @Override
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
    @Override
    public int hashCode() {
        return this.getTitle().hashCode() + this.getBarcode().hashCode() + this.getDirector().hashCode() + this.getFsk();
    }

}
