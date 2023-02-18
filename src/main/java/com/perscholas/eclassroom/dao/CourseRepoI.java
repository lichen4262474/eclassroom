package com.perscholas.eclassroom.dao;

import com.perscholas.eclassroom.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepoI extends JpaRepository<Course,Integer> {
}
