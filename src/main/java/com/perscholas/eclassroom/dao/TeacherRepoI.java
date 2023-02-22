package com.perscholas.eclassroom.dao;

import com.perscholas.eclassroom.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TeacherRepoI  extends JpaRepository<Teacher,Integer> {
    @Transactional
    @Modifying
    @Query(value="update teacher set teacher.name = ?1, teacher.email = ?2, teacher.password =?3 where teacher.id = ?4",nativeQuery = true)
    void setTeacherInfoById(String name, String email, String password, Integer userId);
}
