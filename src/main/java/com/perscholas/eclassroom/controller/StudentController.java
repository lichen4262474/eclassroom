package com.perscholas.eclassroom.controller;

import com.perscholas.eclassroom.models.*;
import com.perscholas.eclassroom.repo.AuthGroupRepoI;
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
    public String saveStudent(Model model,@RequestParam String name,@RequestParam String email,@RequestParam String guardianName,@RequestParam String guardianEmail,@RequestParam String password){
        if(teacherService.findTeacherByEmail(email)==null && studentService.findStudentByEmail(email)==null){
        Student student = new Student(name,email,guardianName, guardianEmail,password);
        studentService.saveStudent(student);
        log.warn("Create student "+ student.getName());
        AuthGroup auth = new AuthGroup(student.getEmail(),"student");
        log.warn("create auth + "+ student.getName());
        authGroupRepoI.save(auth);
         }else{
           model.addAttribute("message","This email has already been registered!");
           return "studentregister";
            }
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


    //   announcement data manipulation
    @GetMapping("/course/{courseId}/announcement")
    public String getAnnouncements(@PathVariable("courseId") Integer id,Model model){
        Course course =courseService.getCourseByID(id);
        List<Announcement> announcements = course.getAnouncementList();
        model.addAttribute("announcements",announcements);
        return "student_announcement";
    }

    //    lesson data manipulation
    @GetMapping("/course/{courseId}/lesson")
    public String getLessons(@PathVariable("courseId") Integer id, Model model) {
        List<Lesson> lessons = lessonService.getAllLessonByCourse(id);
        for(Lesson lesson: lessons) {
            log.warn("getting lesson list" + lesson.getTitle());
        }
        model.addAttribute("lessons", lessons);
        return "student_lesson";
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
        return "student_assignment";
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

    //Student Dashboard Data Manipulation
    @GetMapping("/studentclasshome")
    public String showStudentClassHome(Model model){
        return "student_dashboard";
    }

    @GetMapping("/course/{courseId}")
    public String showDashBoard(@PathVariable("courseId") Integer courseId,Principal principal,Model model){
        Student student = studentService.getStudentByEmail(principal.getName());
        Course course = courseService.getCourseByID(courseId);
        List<Integer> studentGradeSummary = submissionService.studentGradeSummary(course,student);
        log.warn("Getting student Grade Summary " + studentGradeSummary.toString());
        int [] studentGradeAnalysis = submissionService.studentGradeAnalysis(course,student);
        log.warn("getting student Grade analysis " + studentGradeAnalysis.toString());
        String[] assignmentsNames = assignmentService.getAssignmentNamesForCourse(course);
        log.warn("Getting assignment names" + assignmentsNames.toString());
        int [] studentGrades = submissionService.getStudentGradesForCourse2(student,course);
        log.warn("Getting students grades" + studentGrades.toString());
        model.addAttribute("course",course);
        model.addAttribute("studentGradeAnalysis",studentGradeAnalysis);
        model.addAttribute("assignmentsNames",assignmentsNames);
        model.addAttribute("studentGrades",studentGrades);
        model.addAttribute("studentGradeSummary",studentGradeSummary);
        return "student_dashboard";
    }

}
