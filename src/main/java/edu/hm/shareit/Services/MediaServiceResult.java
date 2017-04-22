package edu.hm.shareit.Services;

import javax.ws.rs.core.Response.Status;

/**
 * @author Markus Krahl
 */
public enum MediaServiceResult {
    Succes(200, Status.ACCEPTED);
    private int code;
    private Status state;

    /**
     * 
     * @param code int
     * @param state Status
     */
    MediaServiceResult(int code, Status state) {
        this.code = code;
        this.state = state;
    }
    /**
     * 
     * @return int code
     */
    int getCode() {
        return code;
    }
    /**
     * 
     * @return Status
     */
    Status getStatus() {
        return state;
    }

    /*
    MediaServiceResult valueOf(String ans){
        switch (ans){
            case "Succes":
                return Succes;
            case "BadParameter":
                return BadParameter;
            default:
                return BadParameter;
        }
    }

    MediaServiceResult[] values(){
        return new MediaServiceResult[0];
    }
    */
}
