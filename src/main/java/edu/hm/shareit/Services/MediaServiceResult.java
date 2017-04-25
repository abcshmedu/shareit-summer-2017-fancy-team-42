package edu.hm.shareit.Services;


/**
 * @author Markus Krahl
 */
public enum MediaServiceResult {
    SUCCESS(200, "{\"Message\":\"Changes were successful\"}"), DUPLICATE(200, "{\"Message\":\"Current code does already exist.\"}"), 
        ERROR(200, "{\"Message\":\"Error while processing data\"}");
    private final int code;
    private final String state;

    /**
     * 
     * @param code int
     * @param state Status
     */
    MediaServiceResult(int code, String state) {
        this.code = code;
        this.state = state;
    }
    /**
     * 
     * @return int code
     */
    public int getCode() {
        return code;
    }
    /**
     * 
     * @return Status
     */
    public String getStatus() {
        return state;
    }
    
}
