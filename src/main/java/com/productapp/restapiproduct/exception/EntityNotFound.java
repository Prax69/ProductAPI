package com.productapp.restapiproduct.exception;

public class EntityNotFound extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public EntityNotFound(String message) {
        super(message);
    }

    public EntityNotFound(String message, Throwable cause) {
        super(message, cause);
    }
    public EntityNotFound(Throwable cause) {
        super(cause);
    }
}
