package kz.teacher.forge.teacherforge.repository;

import kz.teacher.forge.teacherforge.models.EmailCode;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

//TODO: made table for action history
@Repository
public interface EmailCodeRepository extends CrudRepository<EmailCode , UUID> {

    @Query("select * from email_code where email = :email ORDER BY sending_time DESC LIMIT 1")
    Optional<EmailCode> findEmailCodeByEmail(@Param("email") String email);

}
