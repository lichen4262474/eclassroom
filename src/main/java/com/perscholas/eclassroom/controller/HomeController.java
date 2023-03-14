package com.perscholas.eclassroom.controller;

import com.perscholas.eclassroom.models.Course;
import com.perscholas.eclassroom.models.Student;
import com.perscholas.eclassroom.models.Teacher;
import com.perscholas.eclassroom.repo.AuthGroupRepoI;
import com.perscholas.eclassroom.service.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/index")
    public String showLogin(@RequestParam(value = "error", required = false) String error){
        return "index";
    }

    @GetMapping("/teacherregister")
    public String show(@RequestParam(value = "name", required = false) String name,@RequestParam(value = "email", required = false) String email,@RequestParam(value = "password", required = false) String password)
    {
        return "teacher_register";
    }

    @GetMapping("/studentregister")
    public String show(@RequestParam(value = "name", required = false) String name,@RequestParam(value = "email", required = false) String email,@RequestParam(value = "guardianName", required = false) String guardianName,@RequestParam(value = "guardianEmail", required = false) String guardianEmail,@RequestParam(value = "password", required = false) String password)
    {
        return "student_register";
    }


    @GetMapping("/teacherhome")
    public  String teacherHome(@RequestParam(value = "id", required = false)Integer id,@ModelAttribute Course course, Principal principal, Model model,@RequestParam(value = "newName", required = false)String newName,@RequestParam(value = "newPassword", required = false)String newPassword)
    {
        String email = principal.getName();
        Teacher teacher =teacherService.findTeacherByEmail(email);
        List<Course> courseListRaw = teacher.getCourseList();
        List<Course> courseList = courseListRaw.stream()
                .sorted(Comparator.comparing(Course::getId).reversed())
                .collect(Collectors.toList());
        log.warn("geting courseList");
        model.addAttribute("teacher",teacher);
        model.addAttribute("courseList",courseList);
        model.addAttribute("addcourse", new Course());
        return "teacher_home";
    }

    @GetMapping("/studenthome")
    public String studentHome(Principal principal, Model model,@RequestParam(value = "addCourseId", required = false)Integer addCourseId,@RequestParam(value = "deleteCourseId", required = false)Integer deleteCourseId,@RequestParam(value = "newName", required = false)String newName,@RequestParam(value = "newPassword", required = false)String newPassword,@RequestParam(value = "newGuardianName", required = false)String newGuardianName,@RequestParam(value = "newGuardianEmail", required = false)String newGuardianEmail) {
        String email = principal.getName();
        List<Course> courseListRaw = studentService.findStudentByEmail(email).getCourseList();
        List<Course> courseList = courseListRaw.stream()
                .sorted(Comparator.comparing(Course::getId).reversed())
                .collect(Collectors.toList());
        List<Course> allCourseList = courseService.getAllCourse();
        Student student = studentService.findStudentByEmail(principal.getName());
        model.addAttribute("courseList",courseList);
        model.addAttribute("allCourseList",allCourseList);
        model.addAttribute("student",student);
        return "student_home";
    }



    @GetMapping("/home")
        public RedirectView showHomePage(@RequestParam(value = "error", required = false) String error,ModelMap model, Principal principal) {

        String email = principal.getName(); //get logged in username/email
        RedirectView redirectView = new RedirectView("/teacherhome");
        log.warn("get principal email " + email);
        if (studentService.findStudentByEmail(email)!=null) {
            List<Course> courseList = studentService.findStudentByEmail(email).getCourseList();
            model.addAttribute("courseList",courseList);
            redirectView = new RedirectView("/studenthome");
        } else if (teacherService.findTeacherByEmail(email)!=null) {
            List<Course> courseList= teacherService.findTeacherByEmail(email).getCourseList();
            model.addAttribute("courseList",courseList);
        }
        return redirectView;
    }





}
