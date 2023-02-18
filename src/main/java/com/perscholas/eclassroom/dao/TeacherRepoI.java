package com.perscholas.eclassroom.dao;

import com.perscholas.eclassroom.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepoI  extends JpaRepository<Teacher,Integer> {
}
