package com.perscholas.eclassroom.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Entity
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    LocalDateTime submissionTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
    @NonNull
    String submissionLink;
    Integer grade;
    @ManyToOne
    @NonNull
    Assignment assignment;
    @ManyToOne
    @NonNull
    Student student;
    @ManyToOne
    @NonNull
    Course course;

    public Submission(@NonNull String submissionLink, Integer grade, @NonNull Assignment assignment, @NonNull Student student, @NonNull Course course) {
        this.submissionLink = submissionLink;
        this.grade = grade;
        this.assignment = assignment;
        this.student = student;
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Submission that = (Submission) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
