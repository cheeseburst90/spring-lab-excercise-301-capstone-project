package com.eatza.customer.customerregistration.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomerException.class)
    public ResponseEntity<Object> exception(CustomerException exception) {

        return new ResponseEntity<>("customer not found: ", HttpStatus.NOT_IMPLEMENTED);
    }

}
