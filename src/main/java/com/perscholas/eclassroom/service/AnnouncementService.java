package com.perscholas.eclassroom.service;

import com.perscholas.eclassroom.dao.*;
import com.perscholas.eclassroom.models.Announcement;
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
public class AnnouncementService {
    AnnouncementRepoI announcementRepoI;
    AssignmentRepoI assignmentRepoI;
    CourseRepoI courseRepoI;
    LessonRepoI lessonRepoI;
    StudentRepoI studentRepoI;
    SubmissionRepoI submissionRepoI;
    TeacherRepoI teacherRepoI;

    @Autowired
    public AnnouncementService( AnnouncementRepoI announcementRepoI,
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

    public void saveAnnouncement(Announcement announcement){
        announcementRepoI.save(announcement);
    }

    public void deleteAnnouncement(Integer id){
        announcementRepoI.deleteById(id);
    }

    public void updateAnnouncement(String title, String content,Integer id){
        announcementRepoI.setAnnouncementInfoById(title,content,id);
    }
    public Announcement getAnnouncement(Integer id) throws NoSuchElementException {
        return announcementRepoI.findById(id).orElseThrow();
    }
}
