package com.perscholas.eclassroom.service;

import com.perscholas.eclassroom.models.Course;
import com.perscholas.eclassroom.repo.*;
import com.perscholas.eclassroom.models.Lesson;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonService {
    AnnouncementRepoI announcementRepoI;
    AssignmentRepoI assignmentRepoI;
    CourseRepoI courseRepoI;
    LessonRepoI lessonRepoI;
    StudentRepoI studentRepoI;
    SubmissionRepoI submissionRepoI;
    TeacherRepoI teacherRepoI;

    @Autowired
    public LessonService( AnnouncementRepoI announcementRepoI,
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

    public void saveLesson(Lesson lesson) {
        lessonRepoI.save(lesson);
    }

    public void deleteLesson(Integer id){
        lessonRepoI.deleteById(id);
    }

    public void updateLesson(String title, String content, String resourceLink,Integer id){
       lessonRepoI.setLessonInfoById(title, content, resourceLink,id);
    }
    public Lesson getLesson(Integer id) throws NoSuchElementException {
        return lessonRepoI.findById(id).orElseThrow();
    }
    public List<Lesson> getAllLesson() {
        return lessonRepoI.findAll();
    }

    public List<Lesson> getAllLessonByCourse(Integer id){
        return lessonRepoI.getAllLessonByCourse(id);

    }


}
