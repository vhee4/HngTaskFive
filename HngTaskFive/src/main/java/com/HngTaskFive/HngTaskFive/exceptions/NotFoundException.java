package com.HngTaskFive.HngTaskFive.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends RuntimeException {
    private  HttpStatus status = HttpStatus.BAD_REQUEST;

    public NotFoundException(){
        this("Error Processing Request!");
    }

    public NotFoundException(String message){
        super(message);
    }

    public NotFoundException(String message, HttpStatus status){
        this(message);
        this.status = status;
    }
}