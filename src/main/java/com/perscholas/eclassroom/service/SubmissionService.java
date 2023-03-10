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
import org.springframework.expression.spel.ast.Assign;
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


    public Submission getSubmission(Student student, Assignment assignment){
        return submissionRepoI.findByStudentAndAssignment(student, assignment);
    }

    public List<Submission> getAllSubmissionForAsgmt(Assignment asgmt) {
        return submissionRepoI.findByAssignment(asgmt);
    }


    public List<Submission> getAllSubmissionForCourse(Course course) {
        return submissionRepoI.findByCourse(course);
    }


    public void updateAllGradeForAsgmt(Assignment asgmt, List<Integer> grades) {
        List<Submission> submissionList = this.getAllSubmissionForAsgmt(asgmt);
        for (int i = 0; i < submissionList.size(); i++) {
            submissionList.get(i).setGrade(grades.get(i));
        }
        asgmt.setSubmissionList(submissionList);
        assignmentRepoI.save(asgmt);
    }
//for teacher gradebook usage. Return -1 if it is unsubmitted.Return -2 if submitted but ungraded.
     public List<Integer> getStudentGradesForCourse(Student student,Course course) {
         List<Integer> grades = new ArrayList<>();
         List<Assignment> assignments = course.getAssignmentList();
         for (Assignment each : assignments) {
             Submission submission = submissionRepoI.findByStudentAndAssignment(student, each);
             if (submission == null) {
                 grades.add(-1);
             } else if(submission.getGrade()==null){
                 grades.add(-2);
             }else{
                 grades.add(submission.getGrade());
             }
         }
         return grades;
     }
//     for student dashboard. Return 0 if unsubmitted or ungraded.
    public int[] getStudentGradesForCourse2(Student student,Course course) {
        List<Integer> studentGradeForCourseRaw = this.getStudentGradesForCourse(student, course);
        int[] studentGradeForCourse = new int[studentGradeForCourseRaw.size()];
        for (int i = 0; i < studentGradeForCourse.length; i++) {
            if (studentGradeForCourseRaw.get(i) > -1) {
                studentGradeForCourse[i] = studentGradeForCourseRaw.get(i);
            } else {
                studentGradeForCourse[i] = 0;
            }
        }
        return studentGradeForCourse;
    }



    public List<Integer> studentGradeSummary(Course course,Student student){

        List<Assignment> assignmentList = course.getAssignmentList();
        Integer assignmentCount = Math.toIntExact(assignmentList.stream().count());
        Integer submissionCount = Math.toIntExact(submissionRepoI.findByStudentAndCourse(student,course).stream().count());
        Integer missingAssignmentCount = assignmentCount - submissionCount;
        List<Integer> gradeslist = this.getStudentGradesForCourse(student,course);
        Integer sum = gradeslist.stream().filter((e)->e>-1).reduce(0,(subtotal,element)->subtotal + element);
        Integer gradeCount = Math.toIntExact(gradeslist.stream().filter((e) -> e > -1).count());
        Integer min = 0;
        Integer max=0;
        Integer average = 0;
        try{
                min = gradeslist.stream().filter((e)->e>-1).min(Integer::compare).get();
                max =gradeslist.stream().filter((e)->e>-1).max(Integer::compare).get();
                average = sum/gradeCount;
        }catch(ArithmeticException e){
            e.printStackTrace();
        }catch (NoSuchElementException e){
            e.printStackTrace();
        }
        List<Integer> studentGradeSummary = Arrays.asList(average,max,min,assignmentCount,submissionCount,missingAssignmentCount);
        return  studentGradeSummary;

    }
    public int[] studentGradeAnalysis(Course course,Student student){
        List<Integer> gradeList = this.getStudentGradesForCourse(student,course);
        int[] gradeSummary = {0,0,0,0,0,0,0};
        for (Integer grade:gradeList){
            if (grade >= 90){
                gradeSummary[0]++;
            }else if(grade >=80){
                gradeSummary[1]++;
            }else if(grade >=70){
                gradeSummary[2]++;
            }else if(grade>=60){
                gradeSummary[3]++;
            }else if (grade>=0){
                gradeSummary[4]++;
            }else if(grade==-1){
                gradeSummary[5]++;
            }else{
                gradeSummary[6]++;
            }
        }
        return gradeSummary;

    }

    public int[] courseGradeSummary(Course course){
        List<Integer> gradeList =this.averageForStudents(course);
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

//    public int[] asgmtGradeSummary(Assignment asgmt){
//        List<Submission> submissionList = this.getAllSubmissionForAsgmt(asgmt);
//        List<Integer> gradeList = submissionList.stream().map(submission -> submission.getGrade()).collect(Collectors.toList());
//        int[] gradeSummary = {0,0,0,0,0};
//        for (Integer grade:gradeList){
//            if (grade >= 90){
//                gradeSummary[0]++;
//            }else if(grade >=80){
//                gradeSummary[1]++;
//            }else if(grade >=70){
//                gradeSummary[2]++;
//            }else if(grade>=60){
//                gradeSummary[3]++;
//            }else{
//                gradeSummary[4]++;
//            }
//        }
//        return gradeSummary;
//    }

    public Integer getAverageForCourse(Course course) {
        List<Submission> submissionListRaw = submissionRepoI.findByCourse(course);
        List<Integer> gradesRaw = submissionListRaw.stream().map(e->e.getGrade()).collect(Collectors.toList());
        List<Integer> grades = gradesRaw.stream().filter(e->e!=null).collect(Collectors.toList());
        Integer count = Math.toIntExact(grades.stream().count());
        Integer sum = grades.stream().reduce(0, (subtotal, element) -> subtotal + element);
        Integer average = 0;
        if (count>0){
            average = sum/count;
        }
        return average;
    }

    public List<Integer> averageForStudents(Course course) {
        List<Student> studentList = course.getStudentList();
        List<Integer> gradeList = new ArrayList<>();
        for (Student student : studentList) {
            gradeList.add(this.studentGradeSummary(course, student).get(0));
        }
        return gradeList;
    }

    public List<String> failingStudentNames(Course course){
        List<Student> studentList = course.getStudentList();
        List<String> failingStudentNames = new ArrayList<>();
        for(Student student: studentList){
            if(this.studentGradeSummary(course, student).get(0) < 70){
                failingStudentNames.add(student.getName());
            }
        }
        return failingStudentNames;
    }

    public int[] studentAverageGrades(Course course){
        List<Student> studentList = course.getStudentList();
        int[] studentAverageGrades = new int[studentList.size()];
        for(int i=0;i<studentList.size();i++){
            studentAverageGrades[i] = this.studentGradeSummary(course,studentList.get(i)).get(0);
        }
        return studentAverageGrades;

    }

    public int averageGradeForAssignment(Assignment assignment){
       Integer[] gradeList = assignment.getSubmissionList().stream().filter(e->e.getGrade()!=null).map(e->e.getGrade()).toArray(Integer[]::new);
       int sum =  Arrays.stream(gradeList).reduce(0, (subtotal, element) -> subtotal + element);
       int count = gradeList.length;
       int average = 0;
       if(count>0){
           average = sum/count;
       }
       return average;
    }

    public  int[] averageGradesForAssignments(Course course){
        List<Assignment> assignmentList = course.getAssignmentList();
        int[] gradeList = new int[assignmentList.size()];
        for(int i =0; i<gradeList.length;i++) {
            gradeList[i] = this.averageGradeForAssignment(assignmentList.get(i));
        }
        return gradeList;
    }


}
