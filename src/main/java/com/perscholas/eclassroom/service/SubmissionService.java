package com.perscholas.eclassroom.service;

import com.perscholas.eclassroom.dao.*;
import com.perscholas.eclassroom.models.Assignment;
import com.perscholas.eclassroom.models.Submission;
import com.perscholas.eclassroom.models.Teacher;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubmissionService {
    AnnouncementRepoI announcementRepoI;
    AssignmentRepoI assignmentRepoI;
    CourseRepoI courseRepoI;
    LessonRepoI lessonRepoI;
    StudentRepoI studentRepoI;
    SubmissionRepoI submissionRepoI;
    TeacherRepoI teacherRepoI;

    @Autowired
    public SubmissionService( AnnouncementRepoI announcementRepoI,
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



    public void saveSubmission(Submission submission) {
        submissionRepoI.save(submission);
    }

    public void deleteSubmission(Integer id){
        submissionRepoI.deleteById(id);
    }

    public void updateSubmission(String submissionLink,Integer id){
        submissionRepoI.setSubmissionInfoById(submissionLink,id);
    }
    public Submission getSubmission(Integer id) throws NoSuchElementException {
        return submissionRepoI.findById(id).orElseThrow();
    }
    public void updateGrade(Integer grade, Integer id){
        submissionRepoI.setSubmissionGradeById(grade,id);

    }


}
