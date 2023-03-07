package com.perscholas.eclassroom.controller;

import com.perscholas.eclassroom.models.Course;
import com.perscholas.eclassroom.models.Student;
import com.perscholas.eclassroom.models.Teacher;
import com.perscholas.eclassroom.repo.CourseRepoI;
import com.perscholas.eclassroom.service.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.UUID;

@Controller
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/student")
public class StudentController {
    private final CourseRepoI courseRepoI;
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
                             TeacherService teacherService,
                             CourseRepoI courseRepoI){
        this.announcementService = announcementService;
        this.assignmentService = assignmentService;
        this.courseService = courseService;
        this.lessonService = lessonService;
        this.studentService = studentService;
        this.submissionService = submissionService;
        this.teacherService = teacherService;
        this.courseRepoI = courseRepoI;
    }

    @ModelAttribute
    public void theStudent( Model model){
        model.addAttribute("theStudent", new Student());
    }

    @PostMapping("/addCourse")
    public RedirectView studentAddCourse(@RequestParam(value = "addCourseId", required = true)Integer addCourseId, Principal principal){
        Student student = studentService.findStudentByEmail(principal.getName());
        Course course = courseService.getCourseByID(addCourseId);
        courseService.addCourseForStudent(course,student);
        log.warn("student" + student.getName() + " add Course" + course.getName());

        RedirectView redirectView = new RedirectView("/studenthome");
        return redirectView;
    }
    @PostMapping("/deleteCourse")
    public RedirectView studentDeleteCourse(@RequestParam(value = "deleteCourseId", required = true) Integer deleteCourseId,Principal principal){
        Student student = studentService.findStudentByEmail(principal.getName());
        Course course = courseService.getCourseByID(deleteCourseId);
        courseService.deleteCourseForStudent(course,student);
        log.warn("student" + student.getName() + " delete Course" + course.getName());
        RedirectView redirectView = new RedirectView("/studenthome");
        return redirectView;
    }

    @PostMapping("/updateStudent")
    public RedirectView updateStudent(Principal principal,@RequestParam(value = "newName", required = true)String newName,@RequestParam(value = "newPassword", required = true)String newPassword,@RequestParam(value = "newGuardianName", required = true)String newGuardianName,@RequestParam(value = "newGuardianEmail", required = true)String newGuardianEmail){
        Student student = studentService.findStudentByEmail(principal.getName());

        log.warn("update student: " + student.getName());
        studentService.updateStudent(newName,newPassword,newGuardianName,newGuardianEmail,student.getId());
        RedirectView redirectView = new RedirectView("/studenthome");
        return redirectView;
    }

}
