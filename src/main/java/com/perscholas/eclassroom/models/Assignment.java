package com.perscholas.eclassroom.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
    LocalDateTime postDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
    @NonNull
    @Future
    LocalDate dueDate;
    @OneToMany(mappedBy = "assignment",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<Submission> submissionList = new ArrayList<>();
    @ManyToOne
    @NonNull
    Course course;

    public Assignment(@NonNull String title, @NonNull String content, String resourceLink, @NonNull LocalDate dueDate, @NonNull Course course) {
        this.title = title;
        this.content = content;
        this.resourceLink = resourceLink;
        this.dueDate = dueDate;
        this.course = course;
    }

    public Assignment(@NonNull String title, @NonNull String content, String resourceLink, @NonNull LocalDate dueDate) {
        this.title = title;
        this.content = content;
        this.resourceLink = resourceLink;
        this.dueDate = dueDate;
    }

    public Assignment(Integer id, @NonNull String title, @NonNull String content, String resourceLink, @NonNull LocalDate dueDate, @NonNull Course course) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.resourceLink = resourceLink;
        this.dueDate = dueDate;
        this.course = course;
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
