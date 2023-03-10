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
//        model.addAttribute("msg","This is the default exception message!");
//    }

    @ExceptionHandler(NoSuchElementException.class)
    public RedirectView exceptionHandle(NoSuchElementException ex) {
        log.debug("There is no such element! Please check if your input value is correct!");
        ex.printStackTrace();
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080/index");
        return redirectView;
    }
    @ExceptionHandler(StudentRegisterException.class)
    public String studentRegisterExceptionHandle(Model model,StudentRegisterException ex){
        log.debug("Student Registered Failed");
        ex.printStackTrace();
        model.addAttribute("message","This email has been registered!");
        return "studentregister";
    }

    @ExceptionHandler(TeacherRegisterException.class)
    public String teacherRegisterExceptionHandle(Model model, TeacherRegisterException ex){
        log.debug("Teacher Registered Failed");
        ex.printStackTrace();
        model.addAttribute("message","This email has been registered!");
        return "teacherregister";
    }



}
