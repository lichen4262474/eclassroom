package com.perscholas.eclassroom.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Slf4j
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @NonNull
    String title;
    @NonNull
    String content;
    String resourceLink;
    LocalDateTime postDateTime = LocalDateTime.now();
    @NonNull
    @Future
    LocalDateTime dueDateTime;
    @OneToMany(mappedBy = "assignment",fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    List<Submission> submissionList = new ArrayList<>();
    @ManyToOne
    @NonNull
    Course course;

    public Assignment(@NonNull String title, @NonNull String content, String resourceLink, @NonNull LocalDateTime dueDateTime, @NonNull Course course) {
        this.title = title;
        this.content = content;
        this.resourceLink = resourceLink;
        this.dueDateTime = dueDateTime;
        this.course = course;
    }

    public Assignment(@NonNull String title, @NonNull String content, String resourceLink, @NonNull LocalDateTime dueDateTime) {
        this.title = title;
        this.content = content;
        this.resourceLink = resourceLink;
        this.dueDateTime = dueDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assignment that = (Assignment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void addSubmission(Submission submission){
        this.submissionList.add(submission);
    }
}
