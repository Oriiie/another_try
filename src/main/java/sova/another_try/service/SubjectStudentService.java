package sova.another_try.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sova.another_try.entity.Group;
import sova.another_try.entity.Student;
import sova.another_try.entity.Subject;
import sova.another_try.repository.GroupRepository;
import sova.another_try.repository.StudentRepository;
import sova.another_try.repository.SubjectRepository;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Comparator;
import java.util.List;

import static net.gcardone.junidecode.Junidecode.unidecode;

@Service
public class SubjectStudentService {
    private StudentRepository studentRepository;
    private SubjectRepository subjectRepository;

    @Autowired
    public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Autowired
    public void setGroupRepository(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public void addStudent(Student student) {
        studentRepository.saveAndFlush(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public void deleteStudent(int id) {
        studentRepository.deleteById(id);
    }

    public Student getStudentById(int id) {
        return studentRepository.getOne(id);
    }

    public void addSubject(Subject subject, int id) {
        Student student = studentRepository.getOne(id);
        subject.setStudent(student);
        studentRepository.save(student);
    }

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public List<Subject> getSubjectStudents(int id) {
        Student student = studentRepository.getOne(id);
        List<Subject> subjects = student.getSubjects();
        subjects.sort(Comparator.comparing(Subject::getId));
        return subjects;
    }

    public void deleteSubject(int id) {
        subjectRepository.deleteById(id);
    }

    public Subject getSubjectById(int id) {
        return subjectRepository.getOne(id);
    }

}
