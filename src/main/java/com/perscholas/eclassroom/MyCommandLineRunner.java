package com.perscholas.eclassroom;

import com.perscholas.eclassroom.models.*;
import com.perscholas.eclassroom.repo.AuthGroupRepoI;
import com.perscholas.eclassroom.repo.StudentRepoI;
import com.perscholas.eclassroom.repo.TeacherRepoI;
import com.perscholas.eclassroom.service.*;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.Month;


@Component
    @Slf4j
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public class MyCommandLineRunner implements CommandLineRunner {


       StudentService studentService;
       TeacherService teacherService;
       CourseService courseService;
       AnnouncementService announcementService;
       LessonService lessonService;
       AssignmentService assignmentService;
       SubmissionService submissionService;

        AuthGroupRepoI authGroupRepoI;

@Autowired
    public MyCommandLineRunner(StudentService studentService, TeacherService teacherService, CourseService courseService, AnnouncementService announcementService, LessonService lessonService, AssignmentService assignmentService, SubmissionService submissionService, AuthGroupRepoI authGroupRepoI) {
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.courseService = courseService;
        this.announcementService = announcementService;
        this.lessonService = lessonService;
        this.assignmentService = assignmentService;
        this.submissionService = submissionService;
        this.authGroupRepoI = authGroupRepoI;
    }

    @PostConstruct
        void created() {
            log.warn("=============== My CommandLineRunner Got Created ===============");
        }

        @Override
        public void run(String... args) throws Exception {

            Student student1 = new Student("Jafer", "Jafer@gmail.com", "Jafer Guardian","JaferGuardian@gmail.com","111111");
            Student student2 = new Student("Mohammed", "Mohammed@gmail.com", "Mohammed Guardian","MohammedGuardian@gmail.com","222222");
            Student student3 = new Student("Anjana", "Anjana@gmail.com", "Anjana Guardian","AnjanaGuardian@gmail.com","333333");

            studentService.saveStudent(student1);
            studentService.saveStudent(student2);
            studentService.saveStudent(student3);

            AuthGroup authGroup1 = new AuthGroup("Jafer@gmail.com","student");
            AuthGroup authGroup2 = new AuthGroup("Mohammed@gmail.com","student");
            AuthGroup authGroup3 = new AuthGroup("Anjana@gmail.com","student");

            authGroupRepoI.save(authGroup1);
            authGroupRepoI.save(authGroup2);
            authGroupRepoI.save(authGroup3);


            Teacher teacher1 = new Teacher("Zach", "Zach@gmail.com","111111");
            Teacher teacher2 = new Teacher("Tyron","Tyron@gmail.com","222222");
            Teacher teacher3 = new Teacher("Kevin","Kevin@gmail.com","333333");

            teacherService.saveTeacher(teacher1);
            teacherService.saveTeacher(teacher2);
            teacherService.saveTeacher(teacher3);


            AuthGroup authGroup4 = new AuthGroup("Zach@gmail.com","teacher");
            AuthGroup authGroup5 = new AuthGroup("Tyron@gmail.com","teacher");
            AuthGroup authGroup6 = new AuthGroup("Kevin@gmail.com","teacher");

            authGroupRepoI.save(authGroup4);
            authGroupRepoI.save(authGroup5);
            authGroupRepoI.save(authGroup6);



            Course course1 = new Course("English1","Freshman Course","www.zoom1.com","9:00-10:00AM", teacher1);
            Course course2 = new Course("English2","Sophomore Course","www.zoom2.com","10:00-11:00AM", teacher2);
            Course course3 = new Course("English3","Junior Course","www.zoom3.com","11:00-12:00AM", teacher3);

            courseService.saveCourse(course1);
            courseService.saveCourse(course2);
            courseService.saveCourse(course3);

            Announcement announcement1 = new Announcement("Welcome","Welcome to my class",course1);
            Announcement announcement2 = new Announcement("Hello","Welcome to my class",course2);
            Announcement announcement3 = new Announcement("Hi","Welcome to my class",course3);

            announcementService.saveAnnouncement(announcement1);
            announcementService.saveAnnouncement(announcement2);
            announcementService.saveAnnouncement(announcement3);

            Lesson lesson1 = new Lesson("Lesson1","Content of Lesson1",course1,"www.resource.com");
            Lesson lesson2 = new Lesson("Lesson2","Content of Lesson2",course1,"www.resource.com");
            Lesson lesson3 = new Lesson("Lesson3","Content of Lesson3",course1,"www.resource.com");

            lessonService.saveLesson(lesson1);
            lessonService.saveLesson(lesson2);
            lessonService.saveLesson(lesson3);

            Assignment assignment1 = new Assignment("Assignment1","Content of Assignment1","www.resourse.com", LocalDateTime.of(2023,
                    Month.JULY, 29, 19, 30, 40),course1);
            Assignment assignment2 = new Assignment("Assignment2","Content of Assignment2","www.resourse.com", LocalDateTime.of(2023,
                    Month.JULY, 30, 19, 30, 40),course2);
            Assignment assignment3 = new Assignment("Assignment3","Content of Assignment3","www.resourse.com", LocalDateTime.of(2023,
                    Month.JULY, 31, 19, 30, 40),course2);

            assignmentService.saveAssignment(assignment1);
            assignmentService.saveAssignment(assignment2);
            assignmentService.saveAssignment(assignment3);

            Submission submission1 = new Submission("www.submissionlink",assignment1,student1,course1);
            Submission submission2 = new Submission("www.submissionlink",assignment2,student2,course2);
            Submission submission3 = new Submission("www.submissionlink",assignment3,student3,course3);


        }
    }



