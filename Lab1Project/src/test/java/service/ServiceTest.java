package service;

import domain.Grade;
import domain.Homework;
import domain.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import validation.GradeValidator;
import validation.HomeworkValidator;
import validation.StudentValidator;
import validation.Validator;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    private static Service service;
    private static GradeXMLRepository fileRepository3;

    @org.junit.jupiter.api.BeforeAll
    static void setUp() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Homework> homeworkValidator = new HomeworkValidator();
        Validator<Grade> gradeValidator = new GradeValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "students.xml");
        HomeworkXMLRepository fileRepository2 = new HomeworkXMLRepository(homeworkValidator, "homework.xml");
        fileRepository3 = new GradeXMLRepository(gradeValidator, "grades.xml");

        service = new Service(fileRepository1, fileRepository2, fileRepository3);
    }

    @org.junit.jupiter.api.Test
    void saveStudent() {
        service.deleteStudent("10");
        assertEquals(1, service.saveStudent("10","Pityi",227));
    }

    @org.junit.jupiter.api.Test
    void saveHomework() {
        service.saveHomework("1","File",9,7);
        assertEquals(0, service.saveHomework("1","File",9,7));
    }

    @org.junit.jupiter.api.Test
    void deleteStudent() {
        Iterable<Student> students = service.findAllStudents();

        Student student = students.iterator().next();

        service.deleteStudent(student.getID());

        students = service.findAllStudents();

        assertNotEquals(students.iterator().next(),student);

    }

    @org.junit.jupiter.api.Test
    void updateStudents() {
        service.deleteStudent("111");
        service.saveStudent("111","Okos Bela",227);
        service.updateStudent("111","Buta Bela", 227);
        Iterable<Student> students = service.findAllStudents();

        Iterator<Student> itr = students.iterator();

        Student student = null;
        while (itr.hasNext()){
            Student s = itr.next();
            if (s.getID() == "111"){
                student = s;
            }
        }
        assertTrue("Buta Bela".equals(student.getName()));
    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    void extendDeadlineTest(int value) {
        service.deleteHomework("111");
        service.saveHomework("111", "Nothing",5, 2);

        service.extendDeadline("111",value);

        Iterable<Homework> homeworks = service.findAllHomework();
        Iterator<Homework> itr = homeworks.iterator();

        Homework homework = null;
        while (itr.hasNext()){
            Homework h = itr.next();
            if (h.getID() == "111"){
                homework = h;
            }
        }

        assertEquals(6, homework.getDeadline());
    }
}