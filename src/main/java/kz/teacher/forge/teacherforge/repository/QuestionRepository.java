package kz.teacher.forge.teacherforge.repository;

import kz.teacher.forge.teacherforge.models.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QuestionRepository extends CrudRepository<Question , UUID> {
}
