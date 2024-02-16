package kz.teacher.forge.teacherforge.models.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ReceiveOpt {

    private String code;
    private String email;
    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }
    public String getEmail() {
        return email.toLowerCase();
    }
}
