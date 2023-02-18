package com.perscholas.eclassroom;

import com.perscholas.eclassroom.dao.CourseRepoI;
import com.perscholas.eclassroom.dao.StudentRepoI;
import com.perscholas.eclassroom.models.Course;
import com.perscholas.eclassroom.models.Student;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


    @Component
    @Slf4j
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public class MyCommandLineRunner implements CommandLineRunner {


        StudentRepoI studentRepoI;

        CourseRepoI courseRepoI;


        @Autowired
        public MyCommandLineRunner(StudentRepoI studentRepoI, CourseRepoI courseRepoI) {
            this.studentRepoI = studentRepoI;
            this.courseRepoI = courseRepoI;
        }

        @PostConstruct
        void created() {
            log.warn("=============== My CommandLineRunner Got Created ===============");
        }

        @Override
        public void run(String... args) throws Exception {

            Student student1 = new Student(123, "Jafer", "Jafer@gmail.com", "dsf", null,null);
            Student student2 = new Student(444, "Mohammed", "Mohammed@gmail.com", "sdfa", null,null);
            Student student3 = new Student(555, "Anjana", "Anjana@gmail.com", "563ghf", null,null);

            studentRepoI.save(student1);
            studentRepoI.save(student2);
            studentRepoI.save(student3);

            Course course1 = new Course(1, "java", null,null,null,null,null);
            Course course2 = new Course(2, "spring Boot", null, null, null, null,null);
            Course course3 = new Course(3, "sql", null, null, null, null,null);

            courseRepoI.save(course1);
            courseRepoI.save(course2);
            courseRepoI.save(course3);

        }
    }



