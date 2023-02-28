package com.perscholas.eclassroom.controller;

import com.perscholas.eclassroom.models.Course;
import com.perscholas.eclassroom.models.Student;
import com.perscholas.eclassroom.models.Teacher;
import com.perscholas.eclassroom.repo.AuthGroupRepoI;
import com.perscholas.eclassroom.repo.StudentRepoI;
import com.perscholas.eclassroom.security.AppUserPrincipal;
import com.perscholas.eclassroom.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping
//@SessionAttributes("email")
public class HomeController {

    AnnouncementService announcementService;
    AssignmentService assignmentService;
    CourseService courseService;
    LessonService lessonService;
    StudentService studentService;
    SubmissionService submissionService;
    TeacherService teacherService;

    AuthGroupRepoI authGroupRepoI;

    @Autowired
    public HomeController(AnnouncementService announcementService,
                          AssignmentService assignmentService,
                          CourseService courseService,
                          LessonService lessonService,
                          StudentService studentService,
                          SubmissionService submissionService,
                          TeacherService teacherService,
                          AuthGroupRepoI authGroupRepoI){
        this.announcementService = announcementService;
        this.assignmentService = assignmentService;
        this.courseService = courseService;
        this.lessonService = lessonService;
        this.studentService = studentService;
        this.submissionService = submissionService;
        this.teacherService = teacherService;
        this.authGroupRepoI = authGroupRepoI;
    }


    @GetMapping("/teacherhome")
    public  String teacherHome(@ModelAttribute Course course, List<Course> courseList)
    {

        return "teacherhome";
    }

    @GetMapping("/studenthome")
    public String studentHome(@RequestParam String code, List<Course> courseList ){

        return "studenthome";

    }
    @GetMapping("/teacherregister")
    public String show(Teacher teacher)
    {
        return "teacherregister";
    }
    @GetMapping("/studentregister")
    public String show(Student student)
    {
        return "studentregister";
    }
    @GetMapping("/home")
        public String showHomePage(@RequestParam(value = "error", required = false) String error, Course course,ModelMap model, Principal principal) {

        String email = principal.getName(); //get logged in username/email
        String view="";
        log.warn("get principal email " + email);
        if (studentService.studentExistByEmail(email) > 0) {
            view = "studenthome";
        } else if (teacherService.teacherExistByEmail(email) > 0) {
            view = "teacherhome";
        }
        return view;
    }




    @GetMapping("/index")
    public String showTeacherLogin(@RequestParam(value = "error", required = false) String error){
        return "index";
    }



}
