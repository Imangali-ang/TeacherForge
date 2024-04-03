package kz.teacher.forge.teacherforge.repository;

import kz.teacher.forge.teacherforge.models.ReportWorkTime;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WorkTimeRepository extends CrudRepository<ReportWorkTime , UUID> {

    @Query("select * from work_time where report_id=:reportId ORDER BY work_date DESC")
    List<ReportWorkTime> findAllByReportId(@Param("reportId") UUID reportId);
}
