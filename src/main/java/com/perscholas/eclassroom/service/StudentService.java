package com.perscholas.eclassroom.service;

import com.perscholas.eclassroom.repo.*;
import com.perscholas.eclassroom.models.Student;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentService {
    AnnouncementRepoI announcementRepoI;
    AssignmentRepoI assignmentRepoI;
    CourseRepoI courseRepoI;
    LessonRepoI lessonRepoI;
    StudentRepoI studentRepoI;
    SubmissionRepoI submissionRepoI;
    TeacherRepoI teacherRepoI;

    @Autowired
    public StudentService( AnnouncementRepoI announcementRepoI,
                                AssignmentRepoI assignmentRepoI,
                                CourseRepoI courseRepoI,
                                LessonRepoI lessonRepoI,
                                StudentRepoI studentRepoI,
                                SubmissionRepoI submissionRepoI,
                                TeacherRepoI teacherRepoI){

        this.announcementRepoI = announcementRepoI;
        this.assignmentRepoI = assignmentRepoI;
        this.courseRepoI = courseRepoI;
        this.lessonRepoI = lessonRepoI;
        this.studentRepoI = studentRepoI;
        this.teacherRepoI = teacherRepoI;
        this.submissionRepoI = submissionRepoI;
    }

    public void saveStudent(Student student) {
        studentRepoI.save(student);
    }

    public void deleteStudent(Integer id){
        studentRepoI.deleteById(id);
    }

    public Student findStudentByEmail(String email){
        return studentRepoI.findByEmail(email);
    }

    public Integer studentExistByEmail(String email){
        return studentRepoI.existByEmail(email);
    }

    public void updateStudent(String name, String password,String guardianName, String guardianEmail,Integer id){
        String bCryptPassword =  new BCryptPasswordEncoder(4).encode(password);
        studentRepoI.setStudentInfoById(name,bCryptPassword,guardianName,guardianEmail,id);
    }

    public Student getStudent(Integer id) throws NoSuchElementException {
        return studentRepoI.findById(id).orElseThrow();
    }

    public Student getStudentByEmail(String email) {
        return studentRepoI.getStudentByEmail(email);
    }
//    public List<StudentDTO> getAllStudentsEssInfo() {
//
//        return studentRepoI
//                .findAll()
//                .stream()
//                .map((oneStudent) -> {
//                    return new StudentDTO(oneStudent.getId(), oneStudent.getName(), oneStudent.getEmail(), oneStudent.getGuardianName(), oneStudent.getGuardianName());
//                })
//                .collect(Collectors.toList());
//    }

}
