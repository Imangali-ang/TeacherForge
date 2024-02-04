package kz.teacher.forge.teacherforge.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter @Setter
public class User {

    @Id
    private UUID id;
    private String email;
    private String userName;
    private String middleName;
    private String lastName;
    private Boolean blocked;
    private LocalDateTime created;
    private LocalDateTime lastConnection;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private String password;
    private UUID schoolId;
    private String position;
    private String category;

    public enum UserRole{
        TEACHER,
        PSYCHOLOGIST,
        ADMIN;
    }

}
