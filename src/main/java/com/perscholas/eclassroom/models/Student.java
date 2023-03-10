package com.perscholas.eclassroom.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// test
@Entity
@Slf4j
@NoArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @NonNull
    String name;
    @NonNull
    @Column(unique=true)
    @Email
    String email;
    @NonNull
    String password;
    @NonNull
    String guardianName;
    @NonNull
    @Email
    String guardianEmail;

    @OneToMany(mappedBy = "student",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Submission> submissionList = new ArrayList<>();

    @ToString.Exclude
    @JsonManagedReference
    @ManyToMany (mappedBy = "studentList", fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    List<Course> courseList = new ArrayList<>();

    public Student(@NonNull String name, @NonNull String email, @NonNull String guardianName, @NonNull String guardianEmail, @NonNull String password) {
        this.name = name;
        this.email = email;
        this.password = new BCryptPasswordEncoder(4).encode(password);
        this.guardianName = guardianName;
        this.guardianEmail = guardianEmail;
    }

    public Student(@NonNull String name, @NonNull String email,  @NonNull String guardianName, @NonNull String guardianEmail, @NonNull String password,List<Course> courseList) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.guardianName = guardianName;
        this.guardianEmail = guardianEmail;
        this.courseList = courseList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void addCourse(Course course){
        this.courseList.add(course);
        course.addStudent(this);
    }

    public void deleteCourse(Course course) {
    this.courseList.remove(course);
    course.deleteStudent(this);
    }
    public void addSubmission(Submission submission){
        this.submissionList.add(submission);
    }


}
