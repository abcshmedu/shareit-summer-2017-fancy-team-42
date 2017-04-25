package edu.hm.shareit.models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnit tests for class Disc.
 * @author Thomas Murschall
 *
 */
public class DiscTest {
    private Disc disc;
    private final int fsk = 6;
    /**
     * Setupp for Unit tests.
     */
    @Before
    public void setUp() {
        disc = new Disc("II", "Mustermann", fsk, "Test");
    }
    
    /**
     * Check returned director.
     * @throws Exception 
     */
    @Test
    public void getDirectorTest() throws Exception {
        assertTrue("Mustermann" == disc.getDirector());
    }
    
    /**
     * Check returned barcode.
     * @throws Exception 
     */
    @Test
    public void getBarcodeTest() throws Exception {
        assertTrue("II" == disc.getBarcode());
    }
    
    /**
     * Check returned fsk.
     * @throws Exception 
     */
    @Test
    public void getFskTest() throws Exception {
        assertTrue(fsk == disc.getFsk());
    }
    
    /**
     * All possible tests for equals.
     * @throws Exception 
     */
    @Test
    public void equalsTest() throws Exception {
        assertTrue(disc.equals(disc));
        assertFalse(disc.equals(new Disc("b", "other", fsk, "other Title")));
        assertFalse(disc.equals(new Book("autho", "isbn", "title")));
    }
    
    /**
     * Check Hashfunction.
     * @throws Exception 
     */
    @Test
    public void hashcodeTest() throws Exception {
        assertTrue("II".hashCode() + "Mustermann".hashCode() + "Test".hashCode() + fsk == disc.hashCode());
    }
}
