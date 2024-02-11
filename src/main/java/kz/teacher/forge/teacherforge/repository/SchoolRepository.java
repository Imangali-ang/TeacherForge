package kz.teacher.forge.teacherforge.repository;

import kz.teacher.forge.teacherforge.models.EmailCode;
import kz.teacher.forge.teacherforge.models.School;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SchoolRepository extends JpaRepository<School , UUID> {

    @Query("select ec from EmailCode ec where ec.email = :email ORDER BY ec.sendingTime DESC LIMIT 1")
    Optional<EmailCode> findEmailCodeByEmail(@Param("email") String email);
}
