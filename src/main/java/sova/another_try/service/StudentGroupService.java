package sova.another_try.service;

import net.gcardone.junidecode.Junidecode;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sova.another_try.entity.Group;
import sova.another_try.entity.Student;
import sova.another_try.repository.GroupRepository;
import sova.another_try.repository.StudentRepository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static net.gcardone.junidecode.Junidecode.unidecode;

@Service
public class StudentGroupService {

    private StudentRepository studentRepository;
    private GroupRepository groupRepository;

    @Autowired
    public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Autowired
    public void setGroupRepository(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public void addGroup(Group group) {
        groupRepository.saveAndFlush(group);
    }

    public List<Group> getAllGroups() {
        return groupRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public void deleteGroup(int id) {
        groupRepository.deleteById(id);
    }

    public Group getGroupById(int id) {
        return groupRepository.getOne(id);
    }

    public void addStudent(Student student, int id) {
        Group g = groupRepository.getOne(id);
        student.setGroup(g);
        studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public List<Student> getGroupStudents(int id) {
        Group group = groupRepository.getOne(id);
        List<Student> students = group.getStudents();
        students.sort(Comparator.comparing(Student::getId));
        return students;
    }

    public void deleteStudent(int id) {
        studentRepository.deleteById(id);
    }

    public Student getStudentById(int id) {
        return studentRepository.getOne(id);
    }

    public File getStudentsData(int group_id) {
        List<Student> students = getGroupStudents(group_id);
        try {
            PrintStream printStream = new PrintStream("C:\\Users\\Артем\\IdeaProjects\\another_try\\src\\main\\resources\\GroupData.txt");
            for ( Student student: students) {

                String login = student.getFirstName()+student.getLastName();
                //Если логин или пароль содержит русские символы, для записи и генерации логпасса они транслителируются
                //в английский
                for(int i = 0; i < login.length(); i++) {
                    if(Character.UnicodeBlock.of(login.charAt(i)).equals(Character.UnicodeBlock.CYRILLIC)) {
                        login = unidecode(login);
                    }
                }

                String password = login+"12345#";
                printStream.printf("Student of group: %5s, number: %d.Login: %15s | Password: %10s\n",
                        student.getGroup().getGroupName(), students.indexOf(student) + 1,
                        login, password);
            }

        } catch (IOException e) {
            System.out.println("Error:" + e);
        }
        File file = new File("C:\\Users\\Артем\\IdeaProjects\\another_try\\src\\main\\resources\\GroupData.txt");
        return file;
    }
}
