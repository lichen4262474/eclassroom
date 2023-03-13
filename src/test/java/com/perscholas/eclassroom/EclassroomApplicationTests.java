package com.perscholas.eclassroom;

import com.perscholas.eclassroom.exceptions.InvalidInputException;
import com.perscholas.eclassroom.models.*;
import com.perscholas.eclassroom.service.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
@SpringBootTest
class EclassroomApplicationTests {
	@Autowired
	TeacherService teacherService;
	@Autowired
	StudentService studentService;
	@Autowired
	CourseService courseService;
	@Autowired
	LessonService lessonService;
	@Autowired
	AnnouncementService announcementService;
	@Autowired
	AssignmentService assignmentService;
	@Autowired
	SubmissionService submissionService;

// parameterized test

	@ParameterizedTest
	@DisplayName("get student by email")
	@ValueSource(strings = {"Jafer@gmail.com","Mohammed@gmail.com","Anjana@gmail.com"})
	void getStudentByEmail(String email){
		 Student student = studentService.getStudentByEmail(email);
		 assertThat(student.getEmail()).isEqualTo(email);
	}


   //TeacherServiceTest
	@Test
	void findTeacherByEmail(){
		Teacher teacher1 = new Teacher("Li","li@gmail.com","password");
		teacherService.saveTeacher(teacher1);
		Teacher teacher2 = teacherService.findTeacherByEmail("li@gmail.com");
		assertThat(teacher2).isEqualTo(teacher1);

	}
	@Test
	void updateTeacher(){
		Teacher teacher1 = new Teacher("LiChen","lichen@gmail.com","password");
		teacherService.saveTeacher(teacher1);
		Teacher teacher2 = teacherService.findTeacherByEmail("lichen@gmail.com");
		teacherService.updateTeacher("Chen","1111111", teacher2.getId());
		assertThat(teacherService.findTeacherByEmail("lichen@gmail.com").getName()).isEqualTo("Chen");
	}

	//StudentService Test
	@Test
	void findStudentByEmail(){
		Student student1= new Student("Tim","tim@gmail.com","Tim Guardian","timguardian@gmail.com","password");
		studentService.saveStudent(student1);
		Student student2 = studentService.findStudentByEmail("tim@gmail.com");
		assertThat(student2).isEqualTo(student1);
	}
	@Test
	void updateStudent(){
		Student student1= new Student("Nina","nina@gmail.com","Nina Guardian","ninaguardian@gmail.com","password");
		studentService.saveStudent(student1);
		studentService.updateStudent("Rose","password","guardian new","email@gmail.com",studentService.findStudentByEmail("nina@gmail.com").getId());
		assertThat(studentService.findStudentByEmail("nina@gmail.com").getName()).isEqualTo("Rose");
	}
	//CourseService Test
	@Test
	void addCourseForStudent(){
		Student student1= new Student("Thomas","thomas@gmail.com","thomas Guardian","thomasGuardian@gmail.com","password");
		Course course1 = new Course("Math4","Math class for senors","http://www.zoom.com","8:00-9:00am");
		studentService.saveStudent(student1);
		courseService.saveCourse(course1);
		courseService.addCourseForStudent(course1,student1);
		List<Course> courseList = studentService.getStudentByEmail("thomas@gmail.com").getCourseList();
		assertThat(courseList).contains(course1);
	}

	//LessonService Test
	@Test
	void getLessonExc(){
		assertThatThrownBy(() -> lessonService.getLesson(0)).isInstanceOf(NoSuchElementException.class);
	}
	//AnnouncementService Test
	@Test
	void getAnnouncementExc(){
		assertThatThrownBy(() -> announcementService.getAnnouncement(0)).isInstanceOf(NoSuchElementException.class);
	}
	//Lesson Service Test
	@Test
	void getAllLessonByCourse(){
		Course course = new Course("Social Studies","Junior Course","http://www.zoom.com","4:00-5:00PM");
		Lesson lesson1 = new Lesson("Civil War","Read the article and answer questions",course);
		Lesson lesson2 = new Lesson("WWI","Read article and answer questions",course);
		courseService.saveCourse(course);
		lessonService.saveLesson(lesson1);
		lessonService.saveLesson(lesson2);
		List<Lesson> expected = new ArrayList<>();
		expected.add(lesson1);
		expected.add(lesson2);
		assertThat(lessonService.getAllLessonByCourse(course.getId())).isEqualTo(expected);
	}

	//AssignmentService Test
	@Test
	void getAssignmentNamesForCourse(){
		Course course = courseService.getCourseByID(1);
		String[] expected = {"Assignment1","Assignment2","Assignment3"};
		String[] result = assignmentService.getAssignmentNamesForCourse(course);
		assertThat(result).isEqualTo(expected);
	}

	//SubmissionService Test
	@Test
	void  updateAllGradeForAsgmtExc(){
		Teacher teacher = new Teacher("Li","bsdf@gmail.com","password");
		Course course = new Course("English","Freshman Course","https://perscholas.org","9:00-10:00AM", teacher);
		Student student = new Student("Student","student@gmail.com","Student Guardian","guardian@gmail.com","password");
		Assignment assignment = new Assignment("Assignment","Content of Assignment","https://www.google.com/", LocalDate.of(2023,
				Month.JULY, 29),course);
		Submission submission = new Submission("https://www.wikipedia.com/",assignment,student,course);
		List<Integer> grades = Arrays.asList(120);
		teacherService.saveTeacher(teacher);
		courseService.saveCourse(course);
		studentService.saveStudent(student);
		assignmentService.saveAssignment(assignment);
		submissionService.saveSubmission(submission);
		assertThatThrownBy(() -> submissionService.updateAllGradeForAsgmt(assignment,grades)).isInstanceOf(InvalidInputException.class);
	}

}
