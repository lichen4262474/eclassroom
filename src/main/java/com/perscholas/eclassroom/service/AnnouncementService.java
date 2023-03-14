package com.perscholas.eclassroom.service;

import com.perscholas.eclassroom.models.Course;
import com.perscholas.eclassroom.repo.*;
import com.perscholas.eclassroom.models.Announcement;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public void deleteAnnouncement(Course course, Announcement announcement){
        course.deleteAnnouncement(announcement);
        announcement.setCourse(null);
        courseRepoI.save(course);
        announcementRepoI.save(announcement);
        announcementRepoI.deleteById(announcement.getId());
    }

    public void updateAnnouncement(String title, String content,Integer id){
        announcementRepoI.setAnnouncementInfoById(title,content,id);
    }

    public Announcement getAnnouncement(Integer id) throws NoSuchElementException {
        return announcementRepoI.findById(id).orElseThrow();
    }

    public List<Announcement> getAllAnnouncement() {
        return announcementRepoI.findAll(Sort.by("id").descending());
    }
}
