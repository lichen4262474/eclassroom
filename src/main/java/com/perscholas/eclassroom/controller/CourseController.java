package com.perscholas.eclassroom.controller;

import com.perscholas.eclassroom.models.Course;
import com.perscholas.eclassroom.models.Teacher;
import com.perscholas.eclassroom.service.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/course")
public class CourseController {
    AnnouncementService announcementService;
    AssignmentService assignmentService;
    CourseService courseService;
    LessonService lessonService;
    StudentService studentService;
    SubmissionService submissionService;
    TeacherService teacherService;

    @Autowired
    public CourseController(AnnouncementService announcementService,
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


    @PostMapping("/addCourse")
    public String saveTeacher(@ModelAttribute("course") Course course){
        log.warn("add course : "+ course);
        courseService.saveCourse(course);
        return "teacherHome";
    }
    @GetMapping("/getCourse")
    public String getCourse(@RequestParam Integer id, Model model){
        log.warn("get course: "+ courseService.getCourse(id));
        model.addAttribute("course", courseService.getCourse(id));
        return "teacherHome";
    }

    @PostMapping("/deleteCourse")
    public String deleteTeacher(@RequestParam Integer id){
        log.warn("delete course: " + courseService.getCourse(id));
        courseService.deleteCourse(id);
        return "teacherHome";
    }

    @PostMapping("/updateCourse")
    public String updateCourse(@RequestParam String name , String description, String zoom, String schedule,Integer id){
        log.warn("update course: " + courseService.getCourse(id));
        courseService.updateCourse(name, description,zoom,schedule,id);
        return "teacherHome";
    }

    @GetMapping("/getAllCourseForTeacher")
    public String getAllCourseForTeacher(Model model,Integer id){
        log.warn("getAllCourseForTeacher");
        model.addAttribute(courseService.getAllCourseForTeacher(id));
        return "teacherHome";
    }

    @GetMapping("/getAllCourseForStudent")
    public String getAllCourseForStudent(Model model,Integer id){
        log.warn("getAllCourseForStudent");
        model.addAttribute(courseService.getAllCourseForStudent(id));
        return "studentHome";
    }
}
