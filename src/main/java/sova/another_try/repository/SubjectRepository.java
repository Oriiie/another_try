package sova.another_try.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sova.another_try.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {

}
