package com.perscholas.eclassroom.service;

import com.perscholas.eclassroom.dao.*;
import com.perscholas.eclassroom.models.Course;
import com.perscholas.eclassroom.models.Teacher;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;

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

    public void saveCourse(Course course) {courseRepoI.save(course);
    }

    public void deleteCourse(Integer id){
        courseRepoI.deleteById(id);
    }

    public void updateCourse(String name, String description, String zoom, String schedule, Integer id){
        courseRepoI.setCourseInfoById(name,description,zoom,schedule,id);
    }
    public Course getCourse(Integer id) throws NoSuchElementException {
        return courseRepoI.findById(id).orElseThrow();
    }

    public List<Course> getAllCourseForTeacher(Integer id)throws NoSuchElementException{
        return teacherRepoI.findById(id).orElseThrow().getCourseList();
    }
    public List<Course> getAllCourseForStudent(Integer id)throws NoSuchElementException{
        return studentRepoI.findById(id).orElseThrow().getCourseList();
    }



}
