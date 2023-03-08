package com.perscholas.eclassroom.controller;

import com.perscholas.eclassroom.models.*;
import com.perscholas.eclassroom.repo.AuthGroupRepoI;
import com.perscholas.eclassroom.repo.CourseRepoI;
import com.perscholas.eclassroom.service.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.lang.reflect.Array;
import java.security.Principal;
import java.util.*;

@Controller
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/student")
public class StudentController {
    AuthGroupRepoI authGroupRepoI;
    AnnouncementService announcementService;
    AssignmentService assignmentService;
    CourseService courseService;
    LessonService lessonService;
    StudentService studentService;
    SubmissionService submissionService;
    TeacherService teacherService;

    @Autowired
    public StudentController(AuthGroupRepoI authGroupRepoI, AnnouncementService announcementService, AssignmentService assignmentService, CourseService courseService, LessonService lessonService, StudentService studentService, SubmissionService submissionService, TeacherService teacherService) {
        this.authGroupRepoI = authGroupRepoI;
        this.announcementService = announcementService;
        this.assignmentService = assignmentService;
        this.courseService = courseService;
        this.lessonService = lessonService;
        this.studentService = studentService;
        this.submissionService = submissionService;
        this.teacherService = teacherService;
    }


//student data manipulation
    @PostMapping("/addStudent")
    public String saveStudent(@RequestParam String name,@RequestParam String email,@RequestParam String guardianName,@RequestParam String guardianEmail,@RequestParam String password){
        Student student = new Student(name,email,guardianName, guardianEmail,password);
        studentService.saveStudent(student);
        log.warn("Create student "+ student.getName());
        AuthGroup auth = new AuthGroup(student.getEmail(),"student");
        log.warn("create auth + "+ student.getName());
        authGroupRepoI.save(auth);
//        RedirectView redirectView = new RedirectView("index");
        return "index";
    }
    @PostMapping("/updateStudent")
    public RedirectView updateStudent(Principal principal,@RequestParam(value = "newName", required = true)String newName,@RequestParam(value = "newPassword", required = true)String newPassword,@RequestParam(value = "newGuardianName", required = true)String newGuardianName,@RequestParam(value = "newGuardianEmail", required = true)String newGuardianEmail){
        Student student = studentService.findStudentByEmail(principal.getName());

        log.warn("update student: " + student.getName());
        studentService.updateStudent(newName,newPassword,newGuardianName,newGuardianEmail,student.getId());
        RedirectView redirectView = new RedirectView("/studenthome");
        return redirectView;
    }

    //Course Data Manipulation

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

    @GetMapping("/course/{courseId}")
    public String goToCourse(@PathVariable("courseId") Integer id,Model model){
        Course course = courseService.getCourseByID(id);
        model.addAttribute("course",course);
        return "studentclasshome";
    }

    @GetMapping("/studentclasshome")
    public String showTeacherClassHome(Model model){
        model.addAttribute("course",new Course());
        return "studentclasshome";
    }
    //   announcement data manipulation
    @GetMapping("/course/{courseId}/announcement")
    public String getAnnouncements(@PathVariable("courseId") Integer id,Model model){
        Course course =courseService.getCourseByID(id);
        List<Announcement> announcements = course.getAnouncementList();
        model.addAttribute("announcements",announcements);
        return "studentannouncement";
    }

    //    lesson data manipulation
    @GetMapping("/course/{courseId}/lesson")
    public String getLessons(@PathVariable("courseId") Integer id, Model model) {
        List<Lesson> lessons = lessonService.getAllLessonByCourse(id);
        for(Lesson lesson: lessons) {
            log.warn("getting lesson list" + lesson.getTitle());
        }
        model.addAttribute("lessons", lessons);
        return "studentlesson";
    }

    //    assignment data manipulation
    @GetMapping("/course/{courseId}/assignment")
    public String getAssignments(@PathVariable("courseId") Integer id, Model model,Principal principal){
        Course course =courseService.getCourseByID(id);
        Student student = studentService.findStudentByEmail(principal.getName());
        List<Assignment> assignments = course.getAssignmentList();
        Map<Assignment,Integer> assignmentWithSubmission = new HashMap<>();
        for(int i =0; i< assignments.size();i++){
            try {
                assignmentWithSubmission.put(assignments.get(i), submissionService.getSubmission(student, assignments.get(i)).getGrade());
                log.warn("entry added assignment name: " + assignments.get(i).getTitle() + "grade: " + submissionService.getSubmission(student, assignments.get(i)).getGrade() );
            }catch(NullPointerException ex){
                assignmentWithSubmission.put(assignments.get(i),-1);
            }
        }

        model.addAttribute("assignmentWithSubmission", assignmentWithSubmission);
        return "studentassignment";
    }

    @PostMapping("/course/{courseId}/submitAssignment/{assignmentId}")
    public RedirectView subAssignment(Principal principal,@RequestParam("submissionLink") String submissionLink, @PathVariable("courseId") Integer courseId,@PathVariable("assignmentId") Integer assignmentId){
        Student student = studentService.findStudentByEmail(principal.getName());
        Assignment assignment = assignmentService.getAssignment(assignmentId);
        Course course = courseService.getCourseByID(courseId);
        Submission newSubmission = new Submission(submissionLink,assignment,student,course);
        submissionService.saveSubmission(newSubmission);
        log.warn("A new Submission has been created" + newSubmission.getId());
        RedirectView redirectView = new RedirectView("/student/course/{courseId}/assignment");
        return redirectView;
    }

    @GetMapping("/studentassignment")
    public String showAssignments(Model model){
        return "studentassignment";
    }

}
