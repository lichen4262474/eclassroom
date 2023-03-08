package com.perscholas.eclassroom;

import com.perscholas.eclassroom.models.*;
import com.perscholas.eclassroom.repo.AuthGroupRepoI;
import com.perscholas.eclassroom.repo.CourseRepoI;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;


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

            AuthGroup authGroup1 = new AuthGroup("Jafer@gmail.com","student");
            AuthGroup authGroup2 = new AuthGroup("Mohammed@gmail.com","student");
            AuthGroup authGroup3 = new AuthGroup("Anjana@gmail.com","student");
            AuthGroup authGroup4 = new AuthGroup("Even@gmail.com","student");
            AuthGroup authGroup5 = new AuthGroup("Michael@gmail.com","student");
            AuthGroup authGroup6 = new AuthGroup("Crystal@gmail.com","student");
            AuthGroup authGroup7 = new AuthGroup("Zach@gmail.com","teacher");
            AuthGroup authGroup8 = new AuthGroup("Tyron@gmail.com","teacher");
            AuthGroup authGroup9 = new AuthGroup("Kevin@gmail.com","teacher");

            authGroupRepoI.save(authGroup1);
            authGroupRepoI.save(authGroup2);
            authGroupRepoI.save(authGroup3);
            authGroupRepoI.save(authGroup4);
            authGroupRepoI.save(authGroup5);
            authGroupRepoI.save(authGroup6);
            authGroupRepoI.save(authGroup7);
            authGroupRepoI.save(authGroup8);
            authGroupRepoI.save(authGroup9);



            Teacher teacher1 = new Teacher("Zach", "Zach@gmail.com","111111");
            Teacher teacher2 = new Teacher("Tyron","Tyron@gmail.com","222222");
            Teacher teacher3 = new Teacher("Kevin","Kevin@gmail.com","333333");

            teacherService.saveTeacher(teacher1);
            teacherService.saveTeacher(teacher2);
            teacherService.saveTeacher(teacher3);



            Course course1 = new Course("English1","Freshman Course","www.zoom1.com","9:00-10:00AM", teacher1);
            Course course2 = new Course("English2","Sophomore Course","www.zoom2.com","10:00-11:00AM", teacher1);
            Course course3 = new Course("English3","Junior Course","www.zoom3.com","08:00-09:00AM", teacher1);
            Course course4 = new Course("English4","Senior Course","www.zoom4.com","1:00-2:00AM", teacher1);
            Course course5 = new Course("English5","AP Course","www.zoom5.com","2:00-3:00PM", teacher1);

            courseService.saveCourse(course1);
            courseService.saveCourse(course2);
            courseService.saveCourse(course3);
            courseService.saveCourse(course4);
            courseService.saveCourse(course5);


            Student student1 = new Student("Jafer", "Jafer@gmail.com", "Jafer Guardian","JaferGuardian@gmail.com","111111");
            Student student2 = new Student("Mohammed", "Mohammed@gmail.com", "Mohammed Guardian","MohammedGuardian@gmail.com","222222");
            Student student3 = new Student("Anjana", "Anjana@gmail.com", "Anjana Guardian","AnjanaGuardian@gmail.com","333333");
            Student student4 = new Student("Evan","Even@gmail.com","Evan Guardian","EvenGuardian@gmail.com","444444");
            Student student5 = new Student("Michael","Michael@gmail.com","Michael Guardian","MichaelGuardian@gmail.com","555555");
            Student student6 = new Student("Crystal","Crystal@gmail.com","Crystal Guardian","CrystalGuardian@gmail.com","666666");


            studentService.saveStudent(student1);
            studentService.saveStudent(student2);
            studentService.saveStudent(student3);
            studentService.saveStudent(student4);
            studentService.saveStudent(student5);
            studentService.saveStudent(student6);

            Course coursea = new Course(1,"English1","Freshman Course","www.zoom1.com","9:00-10:00AM", teacher1);
            Course courseb = new Course(2,"English2","Sophomore Course","www.zoom2.com","10:00-11:00AM", teacher1);
            Course coursec = new Course(3,"English3","Junior Course","www.zoom3.com","08:00-09:00AM", teacher1);
            Course coursed = new Course(4,"English4","Senior Course","www.zoom4.com","1:00-2:00AM", teacher1);
            Course coursee = new Course(5,"English5","AP Course","www.zoom5.com","2:00-3:00PM", teacher1);

            courseService.addCourseForStudent(coursea,student1);
            courseService.addCourseForStudent(courseb,student1);
            courseService.addCourseForStudent(coursec,student1);
            courseService.addCourseForStudent(coursed,student1);
            courseService.addCourseForStudent(coursee,student1);

            courseService.addCourseForStudent(coursea,student2);
            courseService.addCourseForStudent(coursea,student3);
            courseService.addCourseForStudent(coursea,student4);
            courseService.addCourseForStudent(coursea,student5);
            courseService.addCourseForStudent(coursea,student6);





            Announcement announcement1 = new Announcement("Welcome","Welcome to my class",course1);
            Announcement announcement2 = new Announcement("Grading Policy","Here is the grading policy for my class",course1);
            Announcement announcement3 = new Announcement("Tutoring Schedule Change","My tutoring hours this week will be changed t0 4:00-5:00PM",course1);

            announcementService.saveAnnouncement(announcement1);
            announcementService.saveAnnouncement(announcement2);
            announcementService.saveAnnouncement(announcement3);

            Lesson lesson1 = new Lesson("Lesson1","Read the instruction here and study the material in the link...",course1,"https://www.google.com/");
            Lesson lesson2 = new Lesson("Lesson2","Read the instruction here and study the material in the link...",course1,"https://www.google.com/");
            Lesson lesson3 = new Lesson("Lesson3","Read the instruction here and study the material in the link...",course1,"https://www.google.com/");

            lessonService.saveLesson(lesson1);
            lessonService.saveLesson(lesson2);
            lessonService.saveLesson(lesson3);

            Assignment assignment1 = new Assignment("Assignment1","Content of Assignment1","https://www.google.com/", LocalDate.of(2023,
                    Month.JULY, 29),course1);
            Assignment assignment2 = new Assignment("Assignment2","Content of Assignment2","https://www.google.com/", LocalDate.of(2023,
                    Month.JULY, 30 ),course1);
            Assignment assignment3 = new Assignment("Assignment3","Content of Assignment3","https://www.google.com/", LocalDate.of(2023,
                    Month.JULY, 31),course1);

            assignmentService.saveAssignment(assignment1);
            assignmentService.saveAssignment(assignment2);
            assignmentService.saveAssignment(assignment3);

            Submission submission1 = new Submission("https://perscholas.org/",100,assignment1,student1,course1);
            Submission submission2 = new Submission("https://www.wikipedia.com/",90,assignment1,student2,course1);
            Submission submission3 = new Submission("https://www.codecademy.com/learn",80,assignment1,student3,course1);
            Submission submission4 = new Submission("https://regex101.com/",70,assignment1,student4,course1);
            Submission submission5 = new Submission("https://www.udemy.com/",60,assignment1,student5,course1);
            Submission submission6 = new Submission("https://www.coursera.org/",50,assignment1,student6,course1);

            Submission submission7 = new Submission("https://perscholas.org/",100,assignment2,student1,course1);
            Submission submission8 = new Submission("https://www.wikipedia.com/",90,assignment2,student2,course1);
            Submission submission9 = new Submission("https://www.codecademy.com/learn",80,assignment2,student3,course1);
            Submission submission10 = new Submission("https://regex101.com/",70,assignment2,student4,course1);
            Submission submission11 = new Submission("https://www.udemy.com/",60,assignment2,student5,course1);
            Submission submission12 = new Submission("https://www.coursera.org/",50,assignment2,student6,course1);

            Submission submission13 = new Submission("https://perscholas.org/",100,assignment3,student1,course1);
            Submission submission14 = new Submission("https://www.wikipedia.com/",90,assignment3,student2,course1);
            Submission submission15 = new Submission("https://www.codecademy.com/learn",80,assignment3,student3,course1);
            Submission submission16 = new Submission("https://regex101.com/",70,assignment3,student4,course1);
            Submission submission17 = new Submission("https://www.udemy.com/",60,assignment3,student5,course1);
            Submission submission18 = new Submission("https://www.coursera.org/",50,assignment3,student6,course1);

            submissionService.saveSubmission(submission1);
            submissionService.saveSubmission(submission2);
            submissionService.saveSubmission(submission3);
            submissionService.saveSubmission(submission4);
            submissionService.saveSubmission(submission5);
            submissionService.saveSubmission(submission6);

            submissionService.saveSubmission(submission7);
            submissionService.saveSubmission(submission8);
            submissionService.saveSubmission(submission9);
            submissionService.saveSubmission(submission10);
            submissionService.saveSubmission(submission11);
            submissionService.saveSubmission(submission12);

            submissionService.saveSubmission(submission13);
            submissionService.saveSubmission(submission14);
            submissionService.saveSubmission(submission15);
            submissionService.saveSubmission(submission16);
            submissionService.saveSubmission(submission17);
            submissionService.saveSubmission(submission18);


        }
    }



