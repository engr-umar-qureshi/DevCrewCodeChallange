package com.umar.devcrewcodechallange.utility;

import com.umar.devcrewcodechallange.interfaces.ResponseType;

/**
 * Created by UmarQasim on 9/20/2016.
 */
public class ErrorResponse {

    private ResponseType responseType;
    private String responseMessage;

    public ErrorResponse(ResponseType responseType, String responseMessage) {
        this.responseType = responseType;
        this.responseMessage = responseMessage;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
