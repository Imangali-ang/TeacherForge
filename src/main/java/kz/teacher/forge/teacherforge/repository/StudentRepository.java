package kz.teacher.forge.teacherforge.repository;

import kz.teacher.forge.teacherforge.models.Student;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StudentRepository extends CrudRepository<Student, UUID> {
    @Query("SELECT * from students where " +
            " (name LIKE '%' || :text || '%' or surname LIKE '%' || :text || '%' or middlename LIKE '%' || :text || '%') ")
    List<Student> findByName(@Param("text") String searchText);
}
