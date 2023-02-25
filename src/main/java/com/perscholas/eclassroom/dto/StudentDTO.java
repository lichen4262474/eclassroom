package com.perscholas.eclassroom.dto;

import com.perscholas.eclassroom.models.Submission;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentDTO {
   Integer id;
   String name;
   String email;
   String guardianName;
   String guardianEmail;

}
