package kz.teacher.forge.teacherforge.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kz.teacher.forge.teacherforge.models.dto.UserDto;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue
    private UUID id;
    private String email;
    @Column(name = "username")
    private String userName;
    @Column(name = "middlename")
    private String middleName;
    @Column(name = "lastname")
    private String lastName;
    private Boolean blocked;
    private LocalDateTime created;
    private LocalDateTime lastConnection;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    @Column(nullable = false)
    private String password;
    @Column(name = "schoolid")
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
