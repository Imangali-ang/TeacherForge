package kz.teacher.forge.teacherforge.models.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
public class Email {

    private String email;
    private String password;

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }
    public String getEmail() {
        return email.toLowerCase();
    }
}
