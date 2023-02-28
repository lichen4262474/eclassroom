package com.perscholas.eclassroom.controller;

import com.perscholas.eclassroom.models.Course;
import com.perscholas.eclassroom.models.Teacher;
import com.perscholas.eclassroom.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/teacher")
public class TeacherController {
    AnnouncementService announcementService;
    AssignmentService assignmentService;
    CourseService courseService;
    LessonService lessonService;
    StudentService studentService;
    SubmissionService submissionService;
    TeacherService teacherService;

    @Autowired
    public TeacherController(AnnouncementService announcementService,
    AssignmentService assignmentService,
    CourseService courseService,
    LessonService lessonService,
    StudentService studentService,
    SubmissionService submissionService,
    TeacherService teacherService){
        this.announcementService = announcementService;
        this.assignmentService = assignmentService;
        this.courseService = courseService;
        this.lessonService = lessonService;
        this.studentService = studentService;
        this.submissionService = submissionService;
        this.teacherService = teacherService;
    }



    @PostMapping("/addTeacher")
    public String saveTeacher(@ModelAttribute("teacher") Teacher teacher, Course course){
        log.warn("add teacher: "+ teacher);
        teacherService.saveTeacher(teacher);
        return "teacherhome";
    }

    @GetMapping("/getTeacher")
    public String getTeacher(@RequestParam Integer id, Model model){
        log.warn("get teacher: "+ teacherService.getTeacher(id));
        model.addAttribute("teacher", teacherService.getTeacher(id));
        return "index";
    }

    @PostMapping("/deleteTeacher")
    public String deleteTeacher(@RequestParam Integer id){
        log.warn("delete teacher: " + teacherService.getTeacher(id));
        teacherService.deleteTeacher(id);
        return "index";
    }

    @PostMapping("/updateTeacher")
    public String updateTeacher(@RequestParam String name,String email,String password,Integer id){
        log.warn("update teacher: " + id);
        teacherService.updateTeacher(name, email,password,id);
        return "index";
    }


}
