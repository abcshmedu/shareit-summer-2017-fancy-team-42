package edu.hm.shareit.models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnit tests for class Medium.
 * @author Thomas Murschall
 *
 */
public class MediumTest {
    private Medium medium;
    
    /**
     * Setup for all tests.
     */
    @Before
    public void setUp() {
        medium = new Medium("Title");
    }
    
    /**
     * Simple toString test.
     * @throws Exception 
     */
    @Test
    public void toStringTest() throws Exception {
        assertTrue("Medium mit dem Titel: Title".equals(medium.toString()));
    }
    /**
     * All possible tests for equals.
     * @throws Exception 
     */
    @Test
    public void equalsTest() throws Exception {
        assertTrue(medium.equals(medium));
        assertFalse(medium.equals(new Medium("other")));
        assertFalse(medium.equals("test"));
    }
    
    /**
     * Simple hascode test.
     * @throws Exception 
     */
    @Test
    public void hascodeTest() throws Exception {
        assertTrue("Title".hashCode() == medium.hashCode());
    }
}
