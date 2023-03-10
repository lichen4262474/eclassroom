package com.perscholas.eclassroom.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StudentRegisterException extends Exception {
    public StudentRegisterException(String message){
        super(message);
    }
}
