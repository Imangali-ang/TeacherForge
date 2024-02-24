package kz.teacher.forge.teacherforge.models.dto;

import kz.teacher.forge.teacherforge.models.User;
import lombok.Data;
import lombok.Setter;

import java.util.UUID;

@Data
public class UserDto {

    private String email;
    private String userName;
    private String middleName;
    private String lastName;
    private User.UserRole userRole;
    private String password;
    private UUID schoolId;
    private String position;
    private String category;
    private String number;

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }
}
