package com.perscholas.eclassroom.controller;

import com.perscholas.eclassroom.service.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/lesson")
public class LessonController {
    AnnouncementService announcementService;
    AssignmentService assignmentService;
    CourseService courseService;
    LessonService lessonService;
    StudentService studentService;
    SubmissionService submissionService;
    TeacherService teacherService;

    @Autowired
    public LessonController(AnnouncementService announcementService,
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
}
