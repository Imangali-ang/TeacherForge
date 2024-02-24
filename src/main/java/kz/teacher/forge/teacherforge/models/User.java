package kz.teacher.forge.teacherforge.models;

import kz.teacher.forge.teacherforge.models.dto.UserDto;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Table(name = "user")
public class User {
    @Id
    private UUID id;
    private String email;
    @Column(value = "username")
    private String userName;
    @Column(value = "middlename")
    private String middleName;
    @Column(value = "lastname")
    private String lastName;
    private Boolean blocked;
    private LocalDateTime created;
    private LocalDateTime lastConnection;
    private UserRole userRole;

    @NonNull
    private String password;
    @Column(value = "schoolid")
    private UUID schoolId;
    private String position;
    private String category;

    public User() {

    }

    public enum UserRole{
        TEACHER,
        PSYCHOLOGIST,
        ADMIN;
    }

    public User(UserDto userDto , String encodedPassword) {
        id = UUID.randomUUID();
        email = userDto.getEmail();
        userName = userDto.getUserName();
        lastName = userDto.getLastName();
        middleName = userDto.getMiddleName();
        blocked = false;
        created = LocalDateTime.now();
        userRole = userDto.getUserRole();
        schoolId = userDto.getSchoolId();
        position = userDto.getPosition();
        category = userDto.getCategory();
        password = encodedPassword;
    }

}
