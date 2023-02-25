package com.perscholas.eclassroom.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

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
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @NonNull
    String name;
    @NonNull
    @Email(message = "Please provide a valid email")
    String email;
    @NonNull
    @Size(min = 6, max = 10)
    String password;

    @OneToMany(mappedBy = "teacher",fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    List<Course> courseList = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return id == teacher.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

   public void addCourse(Course course){
        this.courseList.add(course);
    }
}
