package com.coderdojo.zen.belt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class BeltNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(BeltNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String beltNotFoundHandler(BeltNotFoundException ex) {
        return ex.getMessage();
    }
}
