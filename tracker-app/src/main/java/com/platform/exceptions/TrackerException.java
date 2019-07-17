package com.platform.exceptions;

import lombok.Data;

import javax.ws.rs.core.Response;

@Data
public class TrackerException extends RuntimeException{
    private Response.Status status;
    private String message;

    public TrackerException() {
        super();
    }

    public TrackerException(String message) {
        super(message);
        this.message=message;
        status=Response.Status.INTERNAL_SERVER_ERROR;
    }

    public TrackerException(String message, Response.Status status) {
        super(message);
        this.message=message;
        this.status=status;
    }

    public TrackerException(String message,Throwable cause) {
        super(message,cause);
        this.message=message;
        this.status=Response.Status.INTERNAL_SERVER_ERROR;
    }

    protected TrackerException(String message, Response.Status status,Throwable cause) {
        super(message, cause);
        this.message=message;
        this.status=status;
    }

}
