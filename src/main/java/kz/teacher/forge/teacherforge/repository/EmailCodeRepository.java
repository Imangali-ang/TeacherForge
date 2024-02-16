package kz.teacher.forge.teacherforge.repository;

import kz.teacher.forge.teacherforge.models.EmailCode;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmailCodeRepository extends CrudRepository<EmailCode , UUID> {

    @Query("select ec from EmailCode ec where ec.email = :email ORDER BY ec.sendingTime DESC LIMIT 1")
    Optional<EmailCode> findEmailCodeByEmail(@Param("email") String email);

}
