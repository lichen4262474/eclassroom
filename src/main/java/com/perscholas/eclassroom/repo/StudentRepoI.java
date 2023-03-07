package com.perscholas.eclassroom.repo;

import com.perscholas.eclassroom.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepoI extends JpaRepository<Student,Integer> {
    @Transactional
    @Modifying
    @Query(value="update Student student set student.name = ?1, student.password = ?2, student.guardian_name = ?3, student.guardian_email = ?4 where student.id = ?5",nativeQuery = true)
    void setStudentInfoById(String name, String password, String guardianName, String guardianEmail, Integer id);

    Student findByEmail(String email);
    @Query(value="select count(*) from Student student where student.email = ?1",nativeQuery = true)
    Integer existByEmail(String email);
}
