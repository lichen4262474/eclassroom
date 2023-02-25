package com.perscholas.eclassroom;

import com.perscholas.eclassroom.repo.StudentRepoI;
import com.perscholas.eclassroom.repo.TeacherRepoI;
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

        TeacherRepoI teacherRepoI;


        @Autowired
        public MyCommandLineRunner(StudentRepoI studentRepoI, TeacherRepoI teacherRepoI) {
            this.studentRepoI = studentRepoI;
            this.teacherRepoI = teacherRepoI;
        }

        @PostConstruct
        void created() {
            log.warn("=============== My CommandLineRunner Got Created ===============");
        }

        @Override
        public void run(String... args) throws Exception {

            Student student1 = new Student("Jafer", "Jafer@gmail.com", "dsf","asdf","ss");
            Student student2 = new Student("Mohammed", "Mohammed@gmail.com", "sdfa","sadf","fasdf");
            Student student3 = new Student("Anjana", "Anjana@gmail.com", "563ghf","asdf","asdfa");

            studentRepoI.save(student1);
            studentRepoI.save(student2);
            studentRepoI.save(student3);


        }
    }



