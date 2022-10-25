package com.example.springbootjunit;

import com.example.springbootjunit.model.CollegeStudent;
import com.example.springbootjunit.model.StudentGrades;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class SpringBootJunitApplicationTests {

    private static int count = 0;

    @Autowired
    CollegeStudent student;

    @Autowired
    StudentGrades studentGrades;

    @Autowired
    ApplicationContext applicationContext;

    @BeforeEach
    public void beforeEach() {
        count = count + 1;
        System.out.println("測試開始摟");

        student.setFirstname("Dean");
        student.setLastname("Yang");
        student.setEmailAddress("aaa@mail.com");

        studentGrades.setMathGradeResults(new ArrayList<>(Arrays.asList(100.0, 85.0, 76.5, 91.75)));
        student.setStudentGrades(studentGrades);
    }

    @Test
    @DisplayName("加成績")
    public void addGradeResultsForStudentGrades() {
        assertEquals(353.25, studentGrades.addGradeResultsForSingleClass(
                student.getStudentGrades().getMathGradeResults()
        ));
    }

    @Test
    @DisplayName("驗證錯的成績")
    public void addGradeResultsForStudentGradesAssertNotEquals() {
        assertNotEquals(0, studentGrades.addGradeResultsForSingleClass(
                student.getStudentGrades().getMathGradeResults()
        ));
    }

    @DisplayName("看誰成績好")
    @Test
    public void isGradeGreaterStudentGrades() {
        assertTrue(studentGrades.isGradeGreater(80, 75), "錯了");
        assertFalse(studentGrades.isGradeGreater(89, 92), "錯了");
    }

    @Test
    @DisplayName("沒成績的學生")
    public void createStudentWithoutGrades() {
        CollegeStudent student = applicationContext.getBean("collegeStudent", CollegeStudent.class);
        student.setFirstname("Dean");
        student.setLastname("Yang");
        student.setEmailAddress("bbb@mail.com");
        assertNotNull(student.getFirstname());
        assertNotNull(student.getLastname());
        assertNotNull(student.getEmailAddress());
        assertNull(studentGrades.checkNull(student.getStudentGrades()));
    }

    @DisplayName("確認學生為原型bean")
    @Test
    public void verifyStudentsArePrototypes() {
        CollegeStudent student1 = applicationContext.getBean("collegeStudent", CollegeStudent.class);
        assertNotSame(student, student1);
    }
    @DisplayName("找到分數平均")
    @Test
    public void findGradePointAverage() {
        assertAll("測試假設全部都是真的",
                () -> assertEquals(353.25, studentGrades.addGradeResultsForSingleClass(
                        student.getStudentGrades().getMathGradeResults())),
                ()->assertEquals(88.31,studentGrades.findGradePointAverage(
                        student.getStudentGrades().getMathGradeResults()))
                );
    }
}
