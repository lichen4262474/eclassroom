package com.perscholas.eclassroom.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    @Email
    String email;
    @NonNull
//    @Size(min = 6, max = 10)
    String password;
    @NonNull
    String guardianName;
    @NonNull
//    @Email
    String guardianEmail;

    @OneToMany(mappedBy = "student",fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    List<Submission> submissionList = new ArrayList<>();

    @ToString.Exclude
    @JoinTable(name = "student_course",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"))
    @ManyToMany (fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH })
    List<Course> courseList = new ArrayList<>();

    public Student(@NonNull String name, @NonNull String email, @NonNull String guardianName, @NonNull String guardianEmail, @NonNull String password) {
        this.name = name;
        this.email = email;
        this.password = new BCryptPasswordEncoder(4).encode(password);
        this.guardianName = guardianName;
        this.guardianEmail = guardianEmail;
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

    public void dropCourse(Course course) {
    this.courseList.remove(course);
    course.deleteStudent(this);
    }
    public void addSubmission(Submission submission){
        this.submissionList.add(submission);
    }


}
