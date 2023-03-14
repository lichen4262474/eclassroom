package com.perscholas.eclassroom.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.perscholas.eclassroom.models.Announcement;
import com.perscholas.eclassroom.models.Course;
import com.perscholas.eclassroom.models.Submission;
import com.perscholas.eclassroom.repo.*;
import com.perscholas.eclassroom.models.Assignment;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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

    public void deleteAssignment(Course course, Assignment assignment){
        assignment.setCourse(null);
        assignmentRepoI.save(assignment);
        course.deleteAssignment(assignment);
        courseRepoI.save(course);
        List<Submission> submissions = submissionRepoI.findByAssignment(assignment);
        for(Submission submission:submissions){
            submission.setAssignment(null);
            submission.setCourse(null);
            submission.setStudent(null);
            submissionRepoI.save(submission);
        }
        submissionRepoI.deleteByAssignment(assignment);
        assignmentRepoI.deleteById(assignment.getId());

    }

    public void updateAssignment(String title, String content, String resourceLink, LocalDateTime dueDateTime, Integer id){
        assignmentRepoI.setAssignmentInfoById(title,content,resourceLink,dueDateTime,id);
    }
    public Assignment getAssignment(Integer id) throws NoSuchElementException {
        return assignmentRepoI.findById(id).orElseThrow();
    }
    public List<Assignment> getAllAssignment() {
        return assignmentRepoI.findAll();
    }

    public String[] getAssignmentNamesForCourse(Course course){
        List<Assignment> assignmentList = course.getAssignmentList();
        List<String> assignmentsNamesList = assignmentList.stream().map((assignment)->assignment.getTitle()).collect(Collectors.toList());
        String[] assignmentsNamesArr = assignmentsNamesList.toArray(new String[0]);
        return  assignmentsNamesArr;
    }


}
