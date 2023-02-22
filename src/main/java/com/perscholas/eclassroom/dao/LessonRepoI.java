package com.perscholas.eclassroom.dao;

import com.perscholas.eclassroom.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LessonRepoI extends JpaRepository<Lesson,Integer> {
    @Transactional
    @Modifying
    @Query(value="update Lesson set lesson.title = ?1, lesson.content = ?2, lesson.resourceLink = ?3 where lesson.id = ?4",nativeQuery = true)
    void setLessonInfoById(String title, String content, String resourceLink,Integer id);
}
