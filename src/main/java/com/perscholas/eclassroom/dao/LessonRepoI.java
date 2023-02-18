package com.perscholas.eclassroom.dao;

import com.perscholas.eclassroom.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepoI extends JpaRepository<Lesson,Integer> {
}
