package com.perscholas.eclassroom.service;

import com.perscholas.eclassroom.dao.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssignmentService {
    AnnouncementRepoI announcementRepoI;
    AssignmentRepoI assignmentRepoI;
    CourseRepoI courseRepoI;
    LessonRepoI lessonRepoI;
    StudentRepoI studentRepoI;
    SubmissonRepoI submissonRepoI;
    TeacherRepoI teacherRepoI;

    @Autowired
    public AssignmentService(AnnouncementRepoI announcementRepoI,
                              AssignmentRepoI assignmentRepoI,
                              CourseRepoI courseRepoI,
                              LessonRepoI lessonRepoI,
                              StudentRepoI studentRepoI,
                              SubmissonRepoI submissonRepoI,
                              TeacherRepoI teacherRepoI){

        this.announcementRepoI = announcementRepoI;
        this.assignmentRepoI = assignmentRepoI;
        this.courseRepoI = courseRepoI;
        this.lessonRepoI = lessonRepoI;
        this.studentRepoI = studentRepoI;
        this.teacherRepoI = teacherRepoI;
        this.submissonRepoI = submissonRepoI;
    }




}
