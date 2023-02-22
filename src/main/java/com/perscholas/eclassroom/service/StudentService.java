package com.perscholas.eclassroom.service;

import com.perscholas.eclassroom.dao.*;
import com.perscholas.eclassroom.models.Assignment;
import com.perscholas.eclassroom.models.Student;
import com.perscholas.eclassroom.models.Teacher;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentService {
    AnnouncementRepoI announcementRepoI;
    AssignmentRepoI assignmentRepoI;
    CourseRepoI courseRepoI;
    LessonRepoI lessonRepoI;
    StudentRepoI studentRepoI;
    SubmissionRepoI submissionRepoI;
    TeacherRepoI teacherRepoI;

    @Autowired
    public StudentService( AnnouncementRepoI announcementRepoI,
                                AssignmentRepoI assignmentRepoI,
                                CourseRepoI courseRepoI,
                                LessonRepoI lessonRepoI,
                                StudentRepoI studentRepoI,
                                SubmissionRepoI submissionRepoI,
                                TeacherRepoI teacherRepoI){

        this.announcementRepoI = announcementRepoI;
        this.assignmentRepoI = assignmentRepoI;
        this.courseRepoI = courseRepoI;
        this.lessonRepoI = lessonRepoI;
        this.studentRepoI = studentRepoI;
        this.teacherRepoI = teacherRepoI;
        this.submissionRepoI = submissionRepoI;
    }

    public void saveStudent(Student student) {
        studentRepoI.save(student);
    }

    public void deleteStudent(Integer id){
        studentRepoI.deleteById(id);
    }

    public void updateStudent(String name, String email, String password,String guardianName, String guardianEmail,Integer id){
        studentRepoI.setStudentInfoById(name, email,password,guardianName,guardianEmail,id);
    }

    public Student getStudent(Integer id) throws NoSuchElementException {
        return studentRepoI.findById(id).orElseThrow();
    }


}
