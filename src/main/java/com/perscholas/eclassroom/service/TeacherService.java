package com.perscholas.eclassroom.service;

import com.perscholas.eclassroom.dao.*;
import com.perscholas.eclassroom.models.Announcement;
import com.perscholas.eclassroom.models.Course;
import com.perscholas.eclassroom.models.Lesson;
import com.perscholas.eclassroom.models.Teacher;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    public void updateTeacher(String name, String email, String password,Integer id){
        teacherRepoI.setTeacherInfoById(name, email,password,id);
    }
    public Teacher getTeacher(Integer id) throws NoSuchElementException{
        return teacherRepoI.findById(id).orElseThrow();
    }


}
