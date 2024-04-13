package kz.teacher.forge.teacherforge.repository;

import kz.teacher.forge.teacherforge.models.Appeals;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AppealsRepository extends CrudRepository<Appeals , UUID> {

    @Modifying
    @Query("update appeals set is_read = true where id=:appealId")
    void markAsRead(@Param("appealId") UUID appealId);

    @Modifying
    @Query("update appeals set deleted = true where id=:appealId")
    void markAsDeleted(@Param("appealId") UUID appealId);
}
