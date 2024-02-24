package kz.teacher.forge.teacherforge.repository;


import kz.teacher.forge.teacherforge.models.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {

    @Query("SELECT * FROM users WHERE email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("SELECT * FROM users WHERE id = :id")
    Optional<User> findById(@Param("id") UUID id);
}
