package kz.teacher.forge.teacherforge.repository;

import kz.teacher.forge.teacherforge.models.Question;
import kz.teacher.forge.teacherforge.models.dto.QuestionDto;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuestionRepository extends CrudRepository<Question , UUID> {

    @Query("select * from questions where test_id=:testId and number=:number limit 1")
    Optional<Question> findByTestIdAndNumber(@Param("testId") UUID testId , @Param("number") int number);

    @Query("select * from questions where test_id=:testId")
    List<Question> findByTestId(@Param("testId") UUID testId);
}
