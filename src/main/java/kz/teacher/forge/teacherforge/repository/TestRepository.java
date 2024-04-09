package kz.teacher.forge.teacherforge.repository;

import kz.teacher.forge.teacherforge.models.Test;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TestRepository extends CrudRepository<Test , UUID> {

    @Query("SELECT * from tests where title like '%' || :title || '%'")
    List<Test> searchByTitleIsLike(@Param("title") String title);

    @Query("SELECT * from tests where teacher_ids like '%' || :teacherId || '%' and status='IN_PROCESSING'")
    List<Test> findTestsByTeacherId(@Param("teacherId") String teacherId);


    @Query("SELECT * from tests where school_id=:schoolId and send_all=true")
    List<Test> findTestsBySchoolAndSendAll(@Param("schoolId") UUID schoolId);
}
