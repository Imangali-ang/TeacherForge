package kz.teacher.forge.teacherforge.repository;

import kz.teacher.forge.teacherforge.models.School;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SchoolRepository extends CrudRepository<School , UUID> {

    @Query("SELECT * FROM schools WHERE " +
            "(:name IS NULL OR name LIKE CONCAT('%', :name, '%')) " +
            "AND (:regionId IS NULL OR region_id = :regionId) " +
            "AND (:status IS NULL OR status = :status) " +
            "AND (:type IS NULL OR type = :type)")
    List<School> filter(@Param("name") String name,
                        @Param("regionId") UUID regionId,
                        @Param("status") School.SchoolStatus status,
                        @Param("type") School.SchoolType type);

    @Query("SELECT * from schools where status = :status")
    List<School> findAllByType(@Param("status") School.SchoolStatus STATE);
}
