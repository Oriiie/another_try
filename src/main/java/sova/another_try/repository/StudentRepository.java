package sova.another_try.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sova.another_try.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}
