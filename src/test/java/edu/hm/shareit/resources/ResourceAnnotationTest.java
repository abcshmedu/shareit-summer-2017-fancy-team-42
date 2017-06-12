package edu.hm.shareit.resources;

import static org.junit.Assert.*;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;



/**
 * Test that checks if the annotations were correct.
 * That checks, if it is possible to access the REST-Methods by the defined URI
 * The functionality of the methods behind the REST-API is tested somewhere else.
 * This tests were made to check the ability to reach the method over the specified REST-Interface.
 * @author Markus Krahl
 *
 */
@Ignore
public class ResourceAnnotationTest {

    public static final String APP_URL = "/";
    public static final int PORT = 8082;
    public static final String WEBAPP_DIR = "./src/main/webapp/";

    private String uriBase = "http://localhost:8082/shareit/media";
    private String bookSampleJSON = "{\"title\":\"Casino Roayl\",\"isbn\":\"12345\",\"author\":\"Ian Flemming\"}";
    private String discSampleJSON = "{\"title\":\"Jurassic Park\",\"barcode\":\"12345\",\"director\":\"Steven Spielberg\",\"fsk\":12}";
    private HttpURLConnection connection;

    /**
     * Initialization.
     * @throws Exception 
     */
    @BeforeClass
    public static void setup() throws Exception {
        Server jetty = new Server(PORT);
        jetty.setHandler(new WebAppContext(WEBAPP_DIR, APP_URL));
        jetty.start();
    }

    /**
     * Every status code is 200, when the Request was forwarded.
     * Even if the book-list is empty.
     * @throws Exception 
     */
    @Test
    public void testGetBooks() throws Exception {
        final int codeT = 200;
        connection = (HttpURLConnection) new URL(uriBase + "/books").openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int code = connection.getResponseCode();
        connection.disconnect();
        assertTrue(code == codeT);
    }

    /**
     * Every status code is 200, when the Request was forwarded.
     * Even if the disc-list is empty.
     * @throws Exception 
     */
    @Test
    public void testGetDiscs() throws Exception {
        final int codeT = 200;
        connection = (HttpURLConnection) new URL(uriBase + "/discs").openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int code = connection.getResponseCode();
        connection.disconnect();
        assertTrue(code == codeT);
    }
    /**
     * Every status code is 200, when the Request was forwarded.
     * Even if the book with that isbn does not exist in the list, the returned state has the code 200 anyway.
     * @throws Exception 
     */
    @Test
    public void testGetBookISBN() throws Exception {
        final int codeT = 200;
        connection = (HttpURLConnection) new URL(uriBase + "/books/1").openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int code = connection.getResponseCode();
        connection.disconnect();
        assertTrue(code == codeT);
    }
    /**
     * Every status code is 200, when the Request was forwarded.
     * Even if the disc with that barcode does not exist in the list, the returned state has the code 200 anyway.
     * @throws Exception 
     */
    @Test
    public void testGetDiscCode() throws Exception {
        final int codeT = 200;
        connection = (HttpURLConnection) new URL(uriBase + "/discs/1").openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int code = connection.getResponseCode();
        connection.disconnect();
        assertTrue(code == codeT);
    }
    /**
     * Every status code is 200, when the Request was forwarded.
     * It is not necessary that the book is really added, all status-codes return 200. 
     * @throws Exception 
     */
    @Test
    public void testPostInsertBook() throws Exception {
        final int codeT = 200;
        connection = (HttpURLConnection) new URL(uriBase + "/books").openConnection();
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.connect();
        OutputStream os = connection.getOutputStream();
        os.write(bookSampleJSON.getBytes());
        os.flush();
        int code = connection.getResponseCode();
        connection.disconnect();
        assertTrue(code == codeT);
    }
    /**
     * Every status code is 200, when the Request was forwarded.
     * It is not necessary that the disc is really added, all status-codes return 200.
     * @throws Exception 
     */
    @Test
    public void testPostInsertDisc() throws Exception {
        final int codeT = 200;
        connection = (HttpURLConnection) new URL(uriBase + "/discs").openConnection();
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.connect();
        OutputStream os = connection.getOutputStream();
        os.write(discSampleJSON.getBytes());
        os.flush();
        int code = connection.getResponseCode();
        connection.disconnect();
        assertTrue(code == codeT);
    }
    /**
     * Every status code is 200, when the Request was forwarded.
     * It is not necessary that the specified bool is updated, all status-codes return 200, even if the book is not in the list.
     * @throws Exception 
     */
    @Test
    public void testPutUpdateBook() throws Exception {
        final int codeT = 200;
        connection = (HttpURLConnection) new URL(uriBase + "/books/1").openConnection();
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestMethod("PUT");
        connection.setDoOutput(true);
        connection.connect();
        OutputStream os = connection.getOutputStream();
        os.write(bookSampleJSON.getBytes());
        os.flush();
        int code = connection.getResponseCode();
        connection.disconnect();
        assertTrue(code == codeT);
    }
    /**
     * Every status code is 200, when the Request was forwarded.
     * It is not necessary that the specified disc is updated, all status-codes return 200, even if the disc is not in the list.
     * @throws Exception 
     */
    @Test
    public void testPutUpdateDisc() throws Exception {
        final int codeT = 200;
        connection = (HttpURLConnection) new URL(uriBase + "/discs/1").openConnection();
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestMethod("PUT");
        connection.setDoOutput(true);
        connection.connect();
        OutputStream os = connection.getOutputStream();
        os.write(discSampleJSON.getBytes());
        os.flush();
        int code = connection.getResponseCode();
        connection.disconnect();
        assertTrue(code == codeT);
    }

}
