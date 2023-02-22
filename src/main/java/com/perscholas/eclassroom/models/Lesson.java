package com.perscholas.eclassroom.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Slf4j
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @NonNull
    String title;
    @NonNull
    String content;
    LocalDateTime postDateTime = LocalDateTime.now();
    @ManyToOne
    Course course;
    String resourceLink;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return id.equals(lesson.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Lesson(@NonNull String title, @NonNull String content, String resourceLink) {
        this.title = title;
        this.content = content;
        this.resourceLink = resourceLink;
    }
}
