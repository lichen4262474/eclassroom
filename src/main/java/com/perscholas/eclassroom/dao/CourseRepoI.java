package com.perscholas.eclassroom.dao;

import com.perscholas.eclassroom.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

@Repository
public interface CourseRepoI extends JpaRepository<Course,Integer> {
    @Transactional
    @Modifying
    @Query(value="update Course set course.name = ?1, course.description =?2,course.zoom =?3, course.schedule =?4, where course.id = ?5",nativeQuery = true)
    void setCourseInfoById(String name, String description, String zoom, String schedule, Integer id);
}
