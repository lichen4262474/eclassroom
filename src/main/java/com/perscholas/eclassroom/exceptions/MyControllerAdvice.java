package com.perscholas.eclassroom.exceptions;

import com.perscholas.eclassroom.models.Teacher;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.view.RedirectView;

import java.util.NoSuchElementException;

@ControllerAdvice
@Slf4j
public class MyControllerAdvice {

//    @ModelAttribute
//    public void initModel(Model model){
//        model.addAttribute("msg","Input value is not valid!");
//    }

    @ExceptionHandler(NoSuchElementException.class)
    public RedirectView exceptionHandle1(NoSuchElementException ex) {
        log.debug("There is no such element! ");
        ex.printStackTrace();
//        model.addAttribute("msg","There is no such element");
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080/index");
        return redirectView;
    }

    @ExceptionHandler(InvalidInputException.class)
    public RedirectView exceptionHandel2(InvalidInputException ex){
        log.debug("Input format is wrong!");
        ex.printStackTrace();
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080/index");
        return redirectView;
    }



}
