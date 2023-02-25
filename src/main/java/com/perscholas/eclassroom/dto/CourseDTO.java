package com.perscholas.eclassroom.dto;

import com.perscholas.eclassroom.models.Teacher;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseDTO {

    String name;
    String description;
    String schedule;
    String zoom;
}
