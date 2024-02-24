package kz.teacher.forge.teacherforge.repository;

import kz.teacher.forge.teacherforge.models.Region;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RegionRepository extends CrudRepository<Region , UUID> {

    @Query("select * from region")
    List<Region> findAll();
}
