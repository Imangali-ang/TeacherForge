package kz.teacher.forge.teacherforge.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "email_code")
@Getter
@Setter
public class EmailCode {


    @Id
    private UUID id;

    private String email;

    private String code;

    private LocalDateTime sendingTime;

    private boolean used;

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }
    public String getEmail() {
        return email.toLowerCase();
    }

}
