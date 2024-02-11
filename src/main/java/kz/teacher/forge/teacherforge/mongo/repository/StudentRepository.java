package kz.teacher.forge.teacherforge.mongo.repository;

import kz.teacher.forge.teacherforge.mongo.models.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends MongoRepository<Student , String> {

    @Query("{ 'additionalProperties': { $elemMatch: { 'health': { $exists: true } } } }")
    List<Student> findByHealthInAdditionalFields(@Param("health") String field);

    @Query("{ 'name': ?0 }")
    List<Student> findByName(@Param("name") String name);
}
