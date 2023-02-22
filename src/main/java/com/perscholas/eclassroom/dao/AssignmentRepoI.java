package com.perscholas.eclassroom.dao;

import com.perscholas.eclassroom.models.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface AssignmentRepoI extends JpaRepository<Assignment,Integer> {
    @Transactional
    @Modifying
    @Query(value="update Assignment set assignment.title = ?1, assignment.content = ?2, assignment.resourceLink =?3, assignment.dueDateTime =?4 where announcement.id = ?5",nativeQuery = true)
    void setAssignmentInfoById(String title, String content, String resourceLink, LocalDateTime dueDateTime, Integer id);
}
