package com.perscholas.eclassroom.service;

import com.perscholas.eclassroom.models.Assignment;
import com.perscholas.eclassroom.models.Course;
import com.perscholas.eclassroom.models.Student;
import com.perscholas.eclassroom.repo.*;
import com.perscholas.eclassroom.models.Submission;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.*;
import java.util.stream.Collectors;

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
    public SubmissionService(AnnouncementRepoI announcementRepoI,
                             AssignmentRepoI assignmentRepoI,
                             CourseRepoI courseRepoI,
                             LessonRepoI lessonRepoI,
                             StudentRepoI studentRepoI,
                             SubmissionRepoI submissionRepoI,
                             TeacherRepoI teacherRepoI) {

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

    public void deleteSubmission(Integer id) {
        submissionRepoI.deleteById(id);
    }

    public void updateSubmission(String submissionLink, Integer id) {
        submissionRepoI.setSubmissionInfoById(submissionLink, id);
    }

    public Submission getOneSubmission(Integer id) throws NoSuchElementException {
        return submissionRepoI.findById(id).orElseThrow();
    }

    public List<Submission> getAllSubmissionForAsgmt(Assignment asgmt) {
        return submissionRepoI.findByAssignment(asgmt);
    }

    public List<Submission> getAllSubmissionForStudent(Student student) {
        return submissionRepoI.findByStudent(student);
    }
    public List<Submission> getAllSubmissionForCourse(Course course) {
        return submissionRepoI.findByCourse(course);
    }

    public void updateOneGrade(Integer grade, Integer submissionId) {
        submissionRepoI.setSubmissionGradeById(grade, submissionId);
    }

    public void updateAllGradeForAsgmt(Assignment asgmt, List<Integer> grades) {
        List<Submission> submissionList = this.getAllSubmissionForAsgmt(asgmt);
        for (int i = 0; i < submissionList.size(); i++) {
            submissionList.get(i).setGrade(grades.get(i));
        }
        asgmt.setSubmissionList(submissionList);
        assignmentRepoI.save(asgmt);
    }

    public void updateAllGradeForStudent(Student student, List<Integer> grades) {
        List<Submission> submissionList = this.getAllSubmissionForStudent(student);
        for (int i = 0; i < submissionList.size(); i++) {
            submissionList.get(i).setGrade(grades.get(i));
        }
    }

    public IntSummaryStatistics getGradeStatsForCourse(Course course){
        List<Submission> submissionList = this.getAllSubmissionForCourse(course);
        List<Integer> gradeList = submissionList.stream().map(submission -> submission.getGrade()).collect(Collectors.toList());
        IntSummaryStatistics stats = gradeList.stream()
                .mapToInt((x) -> x)
                .summaryStatistics();
        return stats;
        //IntSummaryStatistics{count=10, sum=129, min=2, average=12.900000, max=29}
    }

    public IntSummaryStatistics getGradeStatsForAsgmt(Assignment asgmt){
        List<Submission> submissionList = this.getAllSubmissionForAsgmt(asgmt);
        List<Integer> gradeList = submissionList.stream().map(submission -> submission.getGrade()).collect(Collectors.toList());
        IntSummaryStatistics stats = gradeList.stream()
                .mapToInt((x) -> x)
                .summaryStatistics();
        return stats;
        //IntSummaryStatistics{count=10, sum=129, min=2, average=12.900000, max=29}
    }

    public IntSummaryStatistics getGradeStatsForStudent(Student student){
        List<Submission> submissionList = this.getAllSubmissionForStudent(student);
        List<Integer> gradeList = submissionList.stream().map(submission -> submission.getGrade()).collect(Collectors.toList());
        IntSummaryStatistics stats = gradeList.stream()
                .mapToInt((x) -> x)
                .summaryStatistics();
        return stats;
        //IntSummaryStatistics{count=10, sum=129, min=2, average=12.900000, max=29}
    }

    public int[] courseGradeSummary(Course course){
        List<Submission> submissionList = this.getAllSubmissionForCourse(course);
        List<Integer> gradeList = submissionList.stream().map(submission -> submission.getGrade()).collect(Collectors.toList());
        int[] gradeSummary = {0,0,0,0,0};
        for (Integer grade:gradeList){
            if (grade >= 90){
                gradeSummary[0]++;
            }else if(grade >=80){
                gradeSummary[1]++;
            }else if(grade >=70){
                gradeSummary[2]++;
            }else if(grade>=60){
                gradeSummary[3]++;
            }else{
                gradeSummary[4]++;
            }
        }
        return gradeSummary;
    }

    public int[] asgmtGradeSummary(Assignment asgmt){
        List<Submission> submissionList = this.getAllSubmissionForAsgmt(asgmt);
        List<Integer> gradeList = submissionList.stream().map(submission -> submission.getGrade()).collect(Collectors.toList());
        int[] gradeSummary = {0,0,0,0,0};
        for (Integer grade:gradeList){
            if (grade >= 90){
                gradeSummary[0]++;
            }else if(grade >=80){
                gradeSummary[1]++;
            }else if(grade >=70){
                gradeSummary[2]++;
            }else if(grade>=60){
                gradeSummary[3]++;
            }else{
                gradeSummary[4]++;
            }
        }
        return gradeSummary;
    }

}
