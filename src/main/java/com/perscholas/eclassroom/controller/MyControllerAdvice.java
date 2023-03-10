package com.perscholas.eclassroom.controller;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.view.RedirectView;

import java.util.NoSuchElementException;

@ControllerAdvice
@Slf4j
public class MyControllerAdvice {


    @ExceptionHandler(Exception.class)
    public RedirectView exceptionHandle(NoSuchElementException ex) {
        log.debug("No such element.Please enter a valid value.");
        ex.printStackTrace();
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080/index");
        return redirectView;
    }

}
