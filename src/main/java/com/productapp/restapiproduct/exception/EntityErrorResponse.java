package com.productapp.restapiproduct.exception;


import lombok.Data;

@Data
public class EntityErrorResponse {

    private int status;
    private String message;
    private long timeStamp;
}
