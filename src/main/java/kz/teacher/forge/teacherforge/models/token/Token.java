package kz.teacher.forge.teacherforge.models.token;

import kz.teacher.forge.teacherforge.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "token")
public class Token {

    @Id
    public UUID id;

    @Column()
    public String token;

    public TokenType tokenType = TokenType.BEARER;

    public boolean revoked;

    public boolean expired;

    public UUID userId;

    public enum TokenType {
        BEARER
    }
}
