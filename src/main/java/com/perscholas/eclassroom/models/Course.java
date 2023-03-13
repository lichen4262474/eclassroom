package com.perscholas.eclassroom.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

import java.io.File;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @NonNull
    String name;
    @NonNull
    String description;
    @NonNull
    String zoom;
    @NonNull
    String schedule;

    @ManyToOne
    Teacher teacher;

    @ToString.Exclude
    @JoinTable(name = "student_course",
            inverseJoinColumns  = @JoinColumn(name = "student_id", referencedColumnName = "id"),
            joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"))
    @ManyToMany (fetch = FetchType.EAGER, cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    List<Student> studentList = new ArrayList<>();
    @OneToMany(mappedBy = "course",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ToString.Exclude
    List<Announcement> anouncementList = new ArrayList<>();
    @OneToMany (mappedBy = "course",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ToString.Exclude
    List<Lesson> lessonList;
    @OneToMany(mappedBy = "course",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ToString.Exclude
    List<Assignment> assignmentList = new ArrayList<>();
    @OneToMany(mappedBy = "course",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ToString.Exclude
    List<Submission> submissionList =new ArrayList<>();


    public Course(@NonNull String name, @NonNull String description, @NonNull String zoom, @NonNull String schedule, Teacher teacher) {
        this.name = name;
        this.description = description;
        this.zoom = zoom;
        this.schedule = schedule;
        this.teacher = teacher;
    }

    public Course(Integer id, @NonNull String name, @NonNull String description, @NonNull String zoom, @NonNull String schedule, Teacher teacher) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.zoom = zoom;
        this.schedule = schedule;
        this.teacher = teacher;
    }

    public Course(Integer id, @NonNull String name, @NonNull String description, @NonNull String zoom, @NonNull String schedule) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.zoom = zoom;
        this.schedule = schedule;
    }

    @Override
    public String toString() {
        return name + " ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void addStudent(Student student) {
        this.studentList.add(student);
    }

    public void deleteStudent(Student student){
        this.studentList.remove(student);
    }
    public void deleteAllStudent(){
        for(Student student:studentList){
            student.deleteCourse(this);
        }
    }
    public void addAnnouncement(Announcement announcement) {
        this.anouncementList.add(announcement);
    }
    public void addLesson(Lesson lesson) {
        this.lessonList.add(lesson);
    }
    public void addAssignment(Assignment assignment) {
        this.assignmentList.add(assignment);
    }
}
