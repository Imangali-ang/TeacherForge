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
}
