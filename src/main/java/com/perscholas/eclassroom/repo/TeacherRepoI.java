package com.perscholas.eclassroom.repo;

import com.perscholas.eclassroom.models.Student;
import com.perscholas.eclassroom.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public interface TeacherRepoI  extends JpaRepository<Teacher,Integer> {
    @Transactional
    @Modifying
    @Query(value="update teacher set teacher.name = ?1, teacher.email = ?2, teacher.password =?3 where teacher.id = ?4",nativeQuery = true)
    void setTeacherInfoById(String name, String email, String password, Integer userId);

    Teacher findByEmail(String email);
    @Query(value="select count(*) from Teacher teacher where teacher.email = ?1",nativeQuery = true)
    Integer existByEmail(String email);
}
