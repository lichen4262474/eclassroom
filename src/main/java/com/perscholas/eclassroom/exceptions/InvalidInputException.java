package com.perscholas.eclassroom.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InvalidInputException extends Exception {
    public InvalidInputException(String message){
        super(message);
    }
}
