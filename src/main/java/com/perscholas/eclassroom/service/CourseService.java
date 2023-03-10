package com.perscholas.eclassroom.service;

import com.perscholas.eclassroom.models.Student;
import com.perscholas.eclassroom.models.Teacher;
import com.perscholas.eclassroom.repo.*;
import com.perscholas.eclassroom.models.Course;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CourseService {
    AnnouncementRepoI announcementRepoI;
    AssignmentRepoI assignmentRepoI;
    CourseRepoI courseRepoI;
    LessonRepoI lessonRepoI;
    StudentRepoI studentRepoI;
    SubmissionRepoI submissionRepoI;
    TeacherRepoI teacherRepoI;

    @Autowired
    public CourseService( AnnouncementRepoI announcementRepoI,
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

    public void saveCourse(Course course) {
        courseRepoI.save(course);
    }

    public void deleteCourse(Course course, Teacher teacher)throws NoSuchElementException{
        teacher.deleteCourse(course);
        course.setTeacher(null);
        courseRepoI.save(course);
        teacherRepoI.save(teacher);
        courseRepoI.delete(course);
    }

    public void updateCourseInfo(String name, String description, String zoom, String schedule,Integer id){
        courseRepoI.setCourseInfoById(name,description,zoom,schedule,id);
    }
    public Course getCourseByID(Integer id) throws NoSuchElementException {
        return courseRepoI.findById(id).orElseThrow();
    }


    public void addCourseForStudent(Course course, Student student) throws NoSuchElementException{
        if(!student.getCourseList().contains(course)){
        student.addCourse(course);}
        studentRepoI.save(student);
    }
    public void deleteCourseForStudent(Course course, Student student) throws NoSuchElementException{
        if(student.getCourseList().contains(course)){
            student.deleteCourse(course);
        studentRepoI.save(student);}

    }

    public List<Course> getAllCourse() {

        return courseRepoI.findAll();
    }
}
