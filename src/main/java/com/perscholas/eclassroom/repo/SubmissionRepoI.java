package com.perscholas.eclassroom.repo;

import com.perscholas.eclassroom.models.Assignment;
import com.perscholas.eclassroom.models.Course;
import com.perscholas.eclassroom.models.Student;
import com.perscholas.eclassroom.models.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public interface SubmissionRepoI extends JpaRepository<Submission,Integer> {
//    @Transactional
//    @Modifying
//    @Query(value="update Submission set submission.submissionLink = ?1 where submission.id = ?2",nativeQuery = true)
//    void setSubmissionInfoById(String submissionLink, Integer id);

//    @Transactional
//    @Modifying
//    @Query(value="update Submission set submission.grade =?1 where submission.id =?2", nativeQuery = true)
//    void setSubmissionGradeById(Integer grade, Integer id);

    List<Submission> findByAssignment(Assignment asgmt);

    List<Submission> findByStudent(Student student);

    List<Submission> findByCourse(Course course);


    Submission findByStudentAndAssignment(Student student, Assignment assignment);

    List<Submission> findByStudentAndCourse(Student student, Course course);

}
