package com.perscholas.eclassroom.repo;

import com.perscholas.eclassroom.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseRepoI extends JpaRepository<Course,Integer> {
    @Transactional
    @Modifying
    @Query(value="update Course set course.name = ?1, course.description =?2,course.zoom =?3, course.weekday =?4, course.classStartTime = ?5,course.classEndTime=?6 where course.id = ?7",nativeQuery = true)
    void setCourseInfoById(String name, String description, String zoom, String weekday, LocalTime classStartTime, LocalTime classEndTime, Integer id);



    Optional<Course> findByCode(UUID code);
}