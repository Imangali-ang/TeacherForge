package kz.teacher.forge.teacherforge.repository;

import kz.teacher.forge.teacherforge.models.File;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FileRepository extends CrudRepository<File, UUID> {
}
