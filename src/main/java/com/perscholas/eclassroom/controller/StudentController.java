package com.perscholas.eclassroom.controller;

import com.perscholas.eclassroom.models.Course;
import com.perscholas.eclassroom.models.Student;
import com.perscholas.eclassroom.models.Teacher;
import com.perscholas.eclassroom.service.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/student")
public class StudentController {
    AnnouncementService announcementService;
    AssignmentService assignmentService;
    CourseService courseService;
    LessonService lessonService;
    StudentService studentService;
    SubmissionService submissionService;
    TeacherService teacherService;

    @Autowired
    public StudentController(AnnouncementService announcementService,
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

    @ModelAttribute
    public void theStudent( Model model){
        model.addAttribute("theStudent", new Student());
    }

    @PostMapping("/addStudent")
    public String saveStudent(@ModelAttribute("student") Student student, @RequestParam UUID code){
        log.warn("add student: "+ student);
        studentService.saveStudent(student);
        return "studenthome";
    }
    @PostMapping("addCourse")
    public String addCourse(@RequestParam UUID code, Student student){
        courseService.addCourseForStudent(code,student);

        log.warn("add course for" + student.getName());
    return "studenthome";
    }
}
