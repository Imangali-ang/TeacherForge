package kz.teacher.forge.teacherforge.repository;

import kz.teacher.forge.teacherforge.models.File;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FileRepository extends CrudRepository<File, UUID> {

    @Query("select * from file where name=:fileName")
    Optional<File> findByName(@Param("fileName") String fileName);
}
