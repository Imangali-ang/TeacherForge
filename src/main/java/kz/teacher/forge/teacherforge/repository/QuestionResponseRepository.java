package kz.teacher.forge.teacherforge.repository;

import kz.teacher.forge.teacherforge.models.QuestionResponse;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuestionResponseRepository extends CrudRepository<QuestionResponse, UUID> {

    @Query("SELECT * from questions_responses where question_id=:questionId")
    Optional<QuestionResponse> findByQuestId(@Param("questionId") UUID questionId);

    @Query("SELECT * from questions_responses where teacher_id=:teacherId")
    List<QuestionResponse> findAllByTeacherId(@Param("teacherId") UUID teacherId);
}
