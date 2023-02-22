package com.perscholas.eclassroom.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

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
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @NonNull
    String name;
    @NonNull
    @ManyToOne
    Teacher teacher;
    @NonNull
    String description;
    @NonNull
    String zoom;
    @NonNull
    String weekday;
    @NonNull
    LocalTime classStartTime;
    @NonNull
    LocalTime classEndTime;

    UUID code;

    File qrCode;

    @ManyToMany(mappedBy = "courseList")
    List<Student> studentList = new ArrayList<>();
    @OneToMany(mappedBy = "course",fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    List<Announcement> anouncementList = new ArrayList<>();
    @OneToMany (mappedBy = "course",fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    List<Lesson> lessonList;
    @OneToMany(mappedBy = "course",fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    List<Assignment> assignmentList = new ArrayList<>();

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
        student.addCourse(this);
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
