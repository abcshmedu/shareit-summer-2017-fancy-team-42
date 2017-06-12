package edu.hm.shareit.Services;


/**
 * @author Markus Krahl
 */
public enum MediaServiceResult {
    SUCCESS(200, "{\"Message\":\"Changes were successful\"}"), DUPLICATE(200, "{\"Message\":\"Current media-number does already exist.\"}"),
    ERROR(200, "{\"Message\":\"Error while processing data\"}"),
    NOTFOUND(200, "{\"Message\":\"media-number does not exist. Make Sure that the media-number in URI and JSON-body is equal. \"}"),
    BADINFORMATION(200, "{\"Message\":\"Service needs more parameter. Please provide them.\"}"),
    BADREQUEST(200, "{\"Message\":\"Changing media number is forbidden.\"}"),
    BADCODE(200, "{\"Message\":\"Media number of that type is forbidden.\"}");

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
