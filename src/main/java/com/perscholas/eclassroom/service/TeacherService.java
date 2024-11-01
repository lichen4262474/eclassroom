package com.perscholas.eclassroom.service;

import com.perscholas.eclassroom.repo.*;
import com.perscholas.eclassroom.models.Teacher;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeacherService {
    AnnouncementRepoI announcementRepoI;
    AssignmentRepoI assignmentRepoI;
    CourseRepoI courseRepoI;
    LessonRepoI lessonRepoI;
    StudentRepoI studentRepoI;
    SubmissionRepoI submissionRepoI;
    TeacherRepoI teacherRepoI;

    @Autowired
    public TeacherService( AnnouncementRepoI announcementRepoI,
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

    public void saveTeacher(Teacher teacher) {
        teacherRepoI.save(teacher);
    }

    public void deleteTeacher(Integer id){
        teacherRepoI.deleteById(id);
    }

    public void updateTeacher(String name, String password,Integer id){
        String bCryptPassword =  new BCryptPasswordEncoder(4).encode(password);
        teacherRepoI.setTeacherInfoById(name,bCryptPassword,id);
    }
    public Teacher getTeacher(Integer id) throws NoSuchElementException{
        return teacherRepoI.findById(id).orElseThrow();
    }
    public Teacher findTeacherByEmail(String email){
        return teacherRepoI.findByEmail(email);
    }


}
