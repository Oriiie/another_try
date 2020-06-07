package sova.another_try.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sova.another_try.entity.Group;

public interface GroupRepository extends JpaRepository<Group, Integer> {
}
