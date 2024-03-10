package kz.teacher.forge.teacherforge.repository;

import kz.teacher.forge.teacherforge.models.Report;
import kz.teacher.forge.teacherforge.models.ReportType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReportTypeRepository extends CrudRepository<ReportType , UUID> {
}
