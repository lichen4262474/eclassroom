package com.perscholas.eclassroom.repo;

import com.perscholas.eclassroom.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface StudentRepoI extends JpaRepository<Student,Integer> {
    @Transactional
    @Modifying
    @Query(value="update Student student set student.name = ?1, student.email = ?2, student.password = ?3, student.guardianName = ?4, student.guardianEmail = ?5 where student.id = ?6",nativeQuery = true)
    void setStudentInfoById(String name, String email, String password, String guardianName, String guardianEmail, Integer id);

}
