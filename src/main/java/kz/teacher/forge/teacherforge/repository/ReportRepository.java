package kz.teacher.forge.teacherforge.repository;

import kz.teacher.forge.teacherforge.models.Report;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReportRepository extends CrudRepository<Report , UUID> {

    @Modifying
    @Query("update reports set status=:action where id=:id")
    public void setStatus(@Param("action") String status , @Param("id") UUID id);
}
