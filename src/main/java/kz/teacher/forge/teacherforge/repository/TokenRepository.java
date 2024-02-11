package kz.teacher.forge.teacherforge.repository;

import java.util.Optional;

import kz.teacher.forge.teacherforge.models.token.Token;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query("select t from Token t where t.token=:token")
    Optional<Token> findByToken(@Param("token") String token);
}
