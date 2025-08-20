package com.productapp.restapiproduct.exception;

import com.productapp.restapiproduct.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ApiResponse<EntityErrorResponse>> handleException(EntityNotFound exc) {

        EntityErrorResponse error = new EntityErrorResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        ApiResponse<EntityErrorResponse> response =
                new ApiResponse<>("NOT_FOUND", "Entity not found", error);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<EntityErrorResponse> handleException(Exception exc) {

        EntityErrorResponse error = new EntityErrorResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage("This is a bad request, please check your input.");
        error.setTimeStamp(System.currentTimeMillis());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }
}
