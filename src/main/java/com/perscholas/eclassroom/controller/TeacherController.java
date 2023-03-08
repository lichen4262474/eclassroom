package com.perscholas.eclassroom.controller;

import com.perscholas.eclassroom.models.*;
import com.perscholas.eclassroom.repo.AnnouncementRepoI;
import com.perscholas.eclassroom.repo.AssignmentRepoI;
import com.perscholas.eclassroom.repo.AuthGroupRepoI;
import com.perscholas.eclassroom.service.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.expression.spel.ast.Assign;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/teacher")
public class TeacherController {
    private final AuthGroupRepoI authGroupRepoI;
    private final AssignmentRepoI assignmentRepoI;
    private final AnnouncementRepoI announcementRepoI;
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
    TeacherService teacherService,
                             AnnouncementRepoI announcementRepoI,
                             AssignmentRepoI assignmentRepoI,
                             AuthGroupRepoI authGroupRepoI){
        this.announcementService = announcementService;
        this.assignmentService = assignmentService;
        this.courseService = courseService;
        this.lessonService = lessonService;
        this.studentService = studentService;
        this.submissionService = submissionService;
        this.teacherService = teacherService;
        this.announcementRepoI = announcementRepoI;
        this.assignmentRepoI = assignmentRepoI;
        this.authGroupRepoI = authGroupRepoI;
    }


//teacher data manipulation
    @PostMapping("/addTeacher")
    public String saveTeacher(@RequestParam String name,@RequestParam String email,@RequestParam String password){
        Teacher teacher = new Teacher(name,email,password);
        teacherService.saveTeacher(teacher);
        log.warn("Create teacher "+ teacher.getName());
        AuthGroup auth = new AuthGroup(teacher.getEmail(),"teacher");
        log.warn("create auth + "+ teacher.getName());
        authGroupRepoI.save(auth);
//        RedirectView redirectView = new RedirectView("index");
        return "index";
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
    public RedirectView updateTeacher(Principal principal,@RequestParam(value = "newName", required = true)String newName,@RequestParam(value = "newPassword", required = true)String newPassword){
        Teacher teacher = teacherService.findTeacherByEmail(principal.getName());
        log.warn("update teacher: " + teacher.getName());
        teacherService.updateTeacher(newName,newPassword, teacher.getId());
        RedirectView redirectView = new RedirectView("/teacherhome");
        return redirectView;
    }

//    course data manipulation
    @PostMapping("/addCourse")
    public RedirectView teacherAddCourse(@ModelAttribute Course addCourse, Principal principal){
        Teacher teacher = teacherService.findTeacherByEmail(principal.getName());
        addCourse.setTeacher(teacher);
        courseService.saveCourse(addCourse);
        log.warn("course added for teacher" + teacher.getName());
        RedirectView redirectView = new RedirectView("/teacherhome");
        return redirectView;
    }

    @PostMapping("/deleteCourse")
    public RedirectView teacherDeleteCourse(@RequestParam(value = "deleteCourseId", required = true) Integer deleteCourseId,Principal principal){
        Teacher teacher = teacherService.findTeacherByEmail(principal.getName());
        Course course = courseService.getCourseByID(deleteCourseId);
        courseService.deleteCourse(course,teacher);
        log.warn("teacher" + teacher.getName() + " delete Course" + course.getName());
        RedirectView redirectView = new RedirectView("/teacherhome");
        return redirectView;
    }

    @GetMapping("/getCourseList")
    public String getCourseList(Model model,Principal principal){
        Teacher teacher = teacherService.findTeacherByEmail(principal.getName());
        model.addAttribute(teacher.getCourseList());
        return "teacherhome";
    }

    @GetMapping("/course/{courseId}")
    public String goToCourse(@PathVariable("courseId") Integer id,Model model){
        Course course = courseService.getCourseByID(id);
        model.addAttribute("course",course);
        return "teacherclasshome";
    }
   @GetMapping("/teacherclasshome")
    public String showTeacherClassHome(Model model){
        model.addAttribute("course",new Course());
        return "teacherclasshome";
   }

//   announcement data manipulation
   @GetMapping("/course/{courseId}/announcement")
    public String getAnnouncements(@PathVariable("courseId") Integer id,Model model){
        Course course =courseService.getCourseByID(id);
       List<Announcement> announcements = course.getAnouncementList();
       Announcement newAnnouncement = new Announcement();
       model.addAttribute("announcements",announcements);
       model.addAttribute("newAnnouncement",newAnnouncement);
       model.addAttribute("editAnnouncement",new Announcement());
        return "teacherannouncement";
   }

   @PostMapping("/course/{courseId}/addAnnouncement")
   public RedirectView addAnnouncement(@ModelAttribute Announcement newAnnouncement,@PathVariable("courseId") Integer id){
    newAnnouncement.setCourse(courseService.getCourseByID(id));
    announcementService.saveAnnouncement(newAnnouncement);
       log.warn("announcement added");
       RedirectView redirectView = new RedirectView("/teacher/course/{courseId}/announcement");
       return redirectView;
   }

    @GetMapping("/course/{courseId}/deleteAnnouncement/{announcementId}")
    public RedirectView deleteAnnouncement(@PathVariable("announcementId") Integer announcementId,@PathVariable("courseId") Integer courseId)
    { announcementService.deleteAnnouncement(announcementId);
        log.warn("Announcement id " + announcementId +" has been deleted");
        RedirectView redirectView = new RedirectView("/teacher/course/{courseId}/announcement");
        return redirectView;
    }

    @PostMapping("/course/{courseId}/editAnnouncement/{announcementId}")
    public RedirectView editAnnouncement(@ModelAttribute Announcement editAnnouncement,@PathVariable("courseId") Integer courseId,@PathVariable("announcementId") Integer announcementId){
        editAnnouncement.setCourse(courseService.getCourseByID(courseId));
        editAnnouncement.setId(announcementId);
        announcementService.saveAnnouncement(editAnnouncement);
        log.warn("announcement edited" + editAnnouncement.getTitle()+editAnnouncement.getContent());
        RedirectView redirectView = new RedirectView("/teacher/course/{courseId}/announcement");
        return redirectView;
    }
    @GetMapping("/teacherannouncement")
    public String showAnnouncements(){
        return "teacherannouncement";
    }

//    lesson data manipulation
    @GetMapping("/course/{courseId}/lesson")
    public String getLessons(@PathVariable("courseId") Integer id, Model model) {
        List<Lesson> lessons = lessonService.getAllLessonByCourse(id);
        Lesson newLesson = new Lesson();
        for(Lesson lesson: lessons) {
            log.warn("getting lesson list" + lesson.getTitle());
        }
        model.addAttribute("lessons", lessons);
        model.addAttribute("editLesson", new Lesson());
        model.addAttribute("newLesson", newLesson);
        return "teacherlesson";
    }

    @PostMapping("/course/{courseId}/addLesson")
    public RedirectView addLesson(@ModelAttribute Lesson newLesson,@PathVariable("courseId") Integer id){
        newLesson.setCourse(courseService.getCourseByID(id));
        lessonService.saveLesson(newLesson);
        log.warn("Lesson added: " + newLesson.getTitle());
        RedirectView redirectView = new RedirectView("/teacher/course/{courseId}/lesson");
        return redirectView;
    }
    @GetMapping("/course/{courseId}/deleteLesson/{lessonId}")
    public RedirectView deleteLesson(@PathVariable("lessonId") Integer lessonId,@PathVariable("courseId") Integer courseId)
    { lessonService.deleteLesson(lessonId);
        log.warn("Lesson " + lessonId +" has been deleted");
        RedirectView redirectView = new RedirectView("/teacher/course/{courseId}/lesson");
        return redirectView;
    }

    @PostMapping("/course/{courseId}/editLesson/{lessonId}")
    public RedirectView editLesson(@ModelAttribute Lesson editLesson,@PathVariable("courseId") Integer courseId,@PathVariable("lessonId") Integer lessonId){
        editLesson.setCourse(courseService.getCourseByID(courseId));
        editLesson.setId(lessonId);
        lessonService.saveLesson(editLesson);
        log.warn("Lesson edited" + editLesson.getTitle()+editLesson.getContent()+editLesson.getResourceLink());
        RedirectView redirectView = new RedirectView("/teacher/course/{courseId}/lesson");
        return redirectView;
    }
    @GetMapping("/teacherlesson")
    public String showLessons(){
        return "teacherlesson";
    }

//    assignment data manipulation
    @GetMapping("/course/{courseId}/assignment")
    public String getAssignments(@PathVariable("courseId") Integer id, Model model){
        Course course =courseService.getCourseByID(id);
        List<Assignment> assignments = course.getAssignmentList();
        Assignment newAssignment = new Assignment();
        model.addAttribute("assignments", assignments);
        model.addAttribute("newAssignment", newAssignment);
        model.addAttribute("editAssignment",new Assignment());
        return "teacherassignment";
    }

    @PostMapping("/course/{courseId}/addAssignment")
    public RedirectView addAssignment(@ModelAttribute Assignment newAssignment,@PathVariable("courseId") Integer id){
        newAssignment.setCourse(courseService.getCourseByID(id));
        assignmentService.saveAssignment(newAssignment);
        log.warn("assignment added: " + newAssignment.getTitle());
        RedirectView redirectView = new RedirectView("/teacher/course/{courseId}/assignment");
        return redirectView;
    }
    @GetMapping("/course/{courseId}/deleteAssignment/{assignmentId}")
    public RedirectView deleteAssignment(@PathVariable("assignmentId") Integer assignmentId,@PathVariable("courseId") Integer courseId)
    { assignmentService.deleteAssignment(assignmentId);
        log.warn("Assignment " + assignmentId +" has been deleted");
        RedirectView redirectView = new RedirectView("/teacher/course/{courseId}/assignment");
        return redirectView;
    }

    @PostMapping("/course/{courseId}/editAssignment/{assignmentId}")
    public RedirectView editAssignment(@ModelAttribute Assignment editAssignment,@PathVariable("courseId") Integer courseId,@PathVariable("assignmentId") Integer assignmentId){
       editAssignment.setCourse(courseService.getCourseByID(courseId));
        editAssignment.setId(assignmentId);
        assignmentService.saveAssignment(editAssignment);
        log.warn("Assignment edited" + editAssignment.getTitle()+editAssignment.getContent()+editAssignment.getResourceLink());
        RedirectView redirectView = new RedirectView("/teacher/course/{courseId}/assignment");
        return redirectView;
    }

    @GetMapping("/teacherassignment")
    public String showAssignments(){
        return "teacherassignment";
    }

    @GetMapping("/course/{courseId}/gradeAssignment/{assignmentId}")
    public String getSubmissions( @RequestParam(value = "gradesArray", required = false) Integer[] gradesArray,@PathVariable("courseId") Integer courseId,@PathVariable("assignmentId") Integer assignmentId,Model model){
        Assignment assignment = assignmentService.getAssignment(assignmentId);
        List<Submission> submissions = assignment.getSubmissionList();
        model.addAttribute("submissions",submissions);
        model.addAttribute("assignment",assignment);
        log.warn("Get submissions for assignment " + assignment.getId() );
        return "teachergradeassignment";
    }

    @GetMapping("/teachergradeassignment")
    public String showSubmissions(){
        return "teachergradeassignment";
    }
    @PostMapping("/course/{courseId}/gradeAssignment/{assignmentId}/grade")
    public RedirectView gradeAssignment(@RequestParam(value = "gradesArray", required = true) Integer[] gradesArray,@PathVariable("courseId") Integer courseId,@PathVariable("assignmentId") Integer assignmentId){
        Assignment assignment = assignmentService.getAssignment(assignmentId);
        submissionService.updateAllGradeForAsgmt(assignment, List.of(gradesArray));
        RedirectView redirectView = new RedirectView("/teacher/course/{courseId}/gradeAssignment/{assignmentId}");
        return redirectView;
    }

//student data manipulation
    @GetMapping("/course/{courseId}/student")
    public String getStudents(@PathVariable("courseId") Integer id,Model model){
        Course course =courseService.getCourseByID(id);
        List<Student> students = course.getStudentList();
        model.addAttribute("students",students);
        return "teacherstudent";
    }
    @GetMapping("course/{courseId}/student/unenroll/{studentId}")
    public RedirectView unenrollStudent(@PathVariable("courseId") Integer courseId,@PathVariable("studentId") Integer studentId){
        Student student = studentService.getStudent(studentId);
        Course course = courseService.getCourseByID(courseId);
        student.deleteCourse(course);
        studentService.saveStudent(student);
        log.warn("unenroll student" + student.getName());
        RedirectView redirectView = new RedirectView("/teacher/course/{courseId}/student");
        return redirectView;
    }

    @GetMapping("/teacherstudent")
    public String showStudents(){
        return "teacherstudent";
    }

    //gradebook access
    @GetMapping("/teachergradebook")
    public String showGradebook(){
        return "teachergradebook";
    }

    @GetMapping("/course/{courseId}/gradebook")
    public String getAllGrades(@PathVariable("courseId") Integer courseId,Model model){
        List<Student> studentList = courseService.getCourseByID(courseId).getStudentList();
        List<Assignment> assignmentList = courseService.getCourseByID(courseId).getAssignmentList();
        model.addAttribute("studentList",studentList);
        model.addAttribute("assignmentList",assignmentList);
        return"teachergradebook";

    }
}
