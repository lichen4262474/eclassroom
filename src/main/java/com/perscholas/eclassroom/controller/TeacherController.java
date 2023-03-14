package com.perscholas.eclassroom.controller;

import com.perscholas.eclassroom.exceptions.InvalidInputException;
import com.perscholas.eclassroom.models.*;
import com.perscholas.eclassroom.repo.AnnouncementRepoI;
import com.perscholas.eclassroom.repo.AssignmentRepoI;
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
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/teacher")
public class TeacherController {
    private final CourseRepoI courseRepoI;
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
                             AuthGroupRepoI authGroupRepoI,
                             CourseRepoI courseRepoI){
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
        this.courseRepoI = courseRepoI;
    }


//teacher data manipulation
    @PostMapping("/addTeacher")
    public String addTeacher(Model model,@RequestParam String name,@RequestParam String email,@RequestParam String password){
        if(teacherService.findTeacherByEmail(email)==null && studentService.findStudentByEmail(email)==null){
        Teacher teacher = new Teacher(name,email,password);
        teacherService.saveTeacher(teacher);
        log.warn("Create teacher "+ teacher.getName());
        AuthGroup auth = new AuthGroup(teacher.getEmail(),"teacher");
        log.warn("create auth + "+ teacher.getName());
        authGroupRepoI.save(auth);
        }else{
            model.addAttribute("message","This email has already been registered!");
            return "teacher_register";
        }
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


//   announcement data manipulation
   @GetMapping("/course/{courseId}/announcement")
    public String getAnnouncements(@PathVariable("courseId") Integer id,Model model){
        Course course =courseService.getCourseByID(id);
       List<Announcement> announcementsRaw = course.getAnouncementList();
       List<Announcement> announcements = announcementsRaw.stream()
               .sorted(Comparator.comparing(Announcement::getId).reversed())
               .collect(Collectors.toList());
       Announcement newAnnouncement = new Announcement();
       model.addAttribute("announcements",announcements);
       model.addAttribute("newAnnouncement",newAnnouncement);
       model.addAttribute("editAnnouncement",new Announcement());
        return "teacher_announcement";
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
    {   Course course = courseService.getCourseByID(courseId);
        Announcement announcement = announcementService.getAnnouncement(announcementId);
        announcementService.deleteAnnouncement(course,announcement);
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
        return "teacher_announcement";
    }

//    lesson data manipulation
    @GetMapping("/course/{courseId}/lesson")
    public String getLessons(@PathVariable("courseId") Integer id, Model model) {
        List<Lesson> lessonsRaw = lessonService.getAllLessonByCourse(id);
        List<Lesson> lessons = lessonsRaw.stream()
                .sorted(Comparator.comparing(Lesson::getId).reversed())
                .collect(Collectors.toList());
        Lesson newLesson = new Lesson();
        for(Lesson lesson: lessons) {
            log.warn("getting lesson list" + lesson.getTitle());
        }
        model.addAttribute("lessons", lessons);
        model.addAttribute("editLesson", new Lesson());
        model.addAttribute("newLesson", newLesson);
        return "teacher_lesson";
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
    {   Course course = courseService.getCourseByID(courseId);
        Lesson lesson = lessonService.getLesson(lessonId);
        lessonService.deleteLesson(course,lesson);
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
        return "teacher_lesson";
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
        return "teacher_assignment";
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
    {   Course course = courseService.getCourseByID(courseId);
        Assignment assignment = assignmentService.getAssignment(assignmentId);
        assignmentService.deleteAssignment(course,assignment);
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
        return "teacher_assignment";
    }

    @GetMapping("/course/{courseId}/gradeAssignment/{assignmentId}")
    public String getSubmissions( @RequestParam(value = "gradesArray", required = false) Integer[] gradesArray,@PathVariable("courseId") Integer courseId,@PathVariable("assignmentId") Integer assignmentId,Model model){
        Assignment assignment = assignmentService.getAssignment(assignmentId);
        List<Submission> submissions = assignment.getSubmissionList();
        model.addAttribute("submissions",submissions);
        model.addAttribute("assignment",assignment);
        log.warn("Get submissions for assignment " + assignment.getId() );
        RedirectView redirectView = new RedirectView("/course/{courseId}/assignment");
        return "teacher_grade_assignment";
    }

    @GetMapping("/teachergradeassignment")
    public String showSubmissions(){
        return "teachergradeassignment";
    }
    @PostMapping("/course/{courseId}/gradeAssignment/{assignmentId}/grade")
    public RedirectView gradeAssignment(@RequestParam(value = "gradesArray", required = true) Integer[] gradesArray,@PathVariable("courseId") Integer courseId,@PathVariable("assignmentId") Integer assignmentId) throws InvalidInputException {
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
        return "teacher_student";
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
        return "teacher_student";
    }

    //gradebook access
    @GetMapping("/teachergradebook")
    public String showGradebook(){
        return "teacher_gradebook";
    }

    @GetMapping("/course/{courseId}/gradebook")
    public String getAllGrades(@PathVariable("courseId") Integer courseId,Model model){
        Course course = courseService.getCourseByID(courseId);
        List<Student> studentList = course.getStudentList();
        List<Assignment> assignmentList = course.getAssignmentList();
        Map<Student,List<Integer>> studentGradesMap = new HashMap<>();
        for(Student student : studentList) {
            studentGradesMap.put(student, submissionService.getStudentGradesForCourse(student,course));
            log.warn( student.getName() + submissionService.getStudentGradesForCourse(student,course));
        }
        model.addAttribute("assignmentList",assignmentList);
        model.addAttribute("studentGradesMap",studentGradesMap);
        return"teacher_gradebook";
    }

    //dashboard

    @GetMapping("/teacherclasshome")
    public String showTeacherClassHome(Model model){
        return "teacherclasshome";
    }

    @GetMapping("/course/{courseId}")
    public String goToCourse(@PathVariable("courseId") Integer id,Model model,@RequestParam(value = "name", required = false) String name, @RequestParam(value = "description", required = false) String description, @RequestParam(value = "zoom", required = false) String zoom,@RequestParam(value = "schedule", required = false) String schedule){
        Course course = courseService.getCourseByID(id);
        Integer studentEnrolled = Math.toIntExact(course.getStudentList().stream().count());
        Integer courseAverage = submissionService.getAverageForCourse(course);
        String failingNames = String.join(",",submissionService.failingStudentNames(course));
        int[] courseGradeSummary = submissionService.courseGradeSummary(course);
        String[] studentNames = course.getStudentList().stream().map(s->s.getName()).toArray(String[]::new);
        int[] studentAverageGrades = submissionService.studentAverageGrades(course);
        String[] assignmentNames = course.getAssignmentList().stream().map(e->e.getTitle()).toArray(String[]::new);
        int[] assignmentAvg = submissionService.averageGradesForAssignments(course);
        model.addAttribute("courseGradeSummary",courseGradeSummary);
        model.addAttribute("studentEnrolled",studentEnrolled);
        model.addAttribute("failingNames",failingNames);
        model.addAttribute("courseAverage",courseAverage);
        model.addAttribute("course",course);
        model.addAttribute("studentNames",studentNames);
        model.addAttribute("studentAverageGrades",studentAverageGrades);
        model.addAttribute("assignmentAvg",assignmentAvg);
        model.addAttribute("assignmentNames",assignmentNames);
        return "teacher_dashboard";
    }

    @PostMapping("/editCourse/{courseId}")
    public RedirectView editCourse(@PathVariable("courseId") Integer id, @RequestParam("name") String name,@RequestParam("description") String description,@RequestParam("zoom") String zoom, @RequestParam("schedule") String schedule ){
        courseService.updateCourseInfo(name,description,zoom,schedule,id);
        RedirectView redirectView = new RedirectView("/teacher/course/{courseId}");
        return redirectView;
    }
}
