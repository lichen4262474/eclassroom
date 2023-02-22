package com.perscholas.eclassroom.service;

import com.perscholas.eclassroom.dao.*;
import com.perscholas.eclassroom.models.Announcement;
import com.perscholas.eclassroom.models.Assignment;
import com.perscholas.eclassroom.models.Teacher;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssignmentService {
    AnnouncementRepoI announcementRepoI;
    AssignmentRepoI assignmentRepoI;
    CourseRepoI courseRepoI;
    LessonRepoI lessonRepoI;
    StudentRepoI studentRepoI;
    SubmissionRepoI submissionRepoI;
    TeacherRepoI teacherRepoI;

    @Autowired
    public AssignmentService(AnnouncementRepoI announcementRepoI,
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

    public void saveAssignment(Assignment assignment) {
        assignmentRepoI.save(assignment);
    }

    public void deleteAssignment(Integer id){
        assignmentRepoI.deleteById(id);
    }

    public void updateAssignment(String title, String content, String resourceLink, LocalDateTime dueDateTime, Integer id){
        assignmentRepoI.setAssignmentInfoById(title,content,resourceLink,dueDateTime,id);
    }
    public Assignment getAssignment(Integer id) throws NoSuchElementException {
        return assignmentRepoI.findById(id).orElseThrow();
    }



}
