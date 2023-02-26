package com.perscholas.eclassroom.service;

import com.perscholas.eclassroom.models.Student;
import com.perscholas.eclassroom.repo.*;
import com.perscholas.eclassroom.dto.CourseDTO;
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

    public void saveCourse(Course course) {courseRepoI.save(course);
    }

    public void deleteCourse(Integer id){
        courseRepoI.deleteById(id);
    }

    public void updateCourseInfo(String name, String description, String zoom, String weekday, LocalTime classStartTime, LocalTime classEndTime, Integer id){
        courseRepoI.setCourseInfoById(name,description,zoom,weekday,classStartTime,classEndTime,id);
    }
    public Course getCourseByID(Integer id) throws NoSuchElementException {
        return courseRepoI.findById(id).orElseThrow();
    }

    public Course getCourseByCode(UUID code) throws  NoSuchElementException{
        return courseRepoI.findByCode(code).orElseThrow();
    }

    public void addCourseForStudent(UUID code, Student student) throws NoSuchElementException{
        Course course = this.getCourseByCode(code);
        course.addStudent(student);
    }
    public List<CourseDTO> getAllCourseEssInfo() {
        return courseRepoI
                .findAll()
                .stream()
                .map((course) -> {
                    return new CourseDTO(course.getName(),course.getDescription(),course.getSchedule(),course.getZoom() );
                })
                .collect(Collectors.toList());
    }

}
