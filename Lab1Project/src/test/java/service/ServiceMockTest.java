package service;

import domain.Homework;
import domain.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class ServiceMockTest {
    private Service service;

    @Mock
    private StudentXMLRepository studentRepository;
    @Mock
    private HomeworkXMLRepository homeworkRepository;
    @Mock
    private GradeXMLRepository gradeRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        service = new Service(studentRepository, homeworkRepository, gradeRepository);
    }

    @Test
    public void testSaveHomework() {
        Homework homework = new Homework("uj hazi", "hazi leiras", 5, 1);
        service.saveHomework(homework.getID(), homework.getDescription(), homework.getDeadline(), homework.getStartline());

        Mockito.verify(homeworkRepository).save(homework);
    }

    @Test
    public void testDeleteHomework() {
        Homework homework = new Homework("uj hazi", "hazi leiras", 5, 1);
        when(homeworkRepository.delete(anyString())).thenReturn(homework);

        assertNotEquals(0, service.deleteHomework(homework.getID()));
    }

    @Test
    public void testDeleteStudent() {
        Student student = new Student("uj diak", "Tudo R. Akos", 531);
        when(studentRepository.delete(anyString())).thenReturn(student);

        assertEquals(1, service.deleteStudent(student.getID()));
    }
}
