package kz.teacher.forge.teacherforge.repository;

import java.util.Optional;

import kz.teacher.forge.teacherforge.models.token.Token;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends CrudRepository<Token, Integer> {

    @Query("select * from token where token=:token")
    Optional<Token> findByToken(@Param("token") String token);
}
