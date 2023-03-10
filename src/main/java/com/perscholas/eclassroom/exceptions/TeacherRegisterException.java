package com.perscholas.eclassroom.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TeacherRegisterException extends Exception{
    public TeacherRegisterException(String message){
        super(message);
    }
}
