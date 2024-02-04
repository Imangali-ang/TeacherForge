package kz.teacher.forge.teacherforge.models;

import jakarta.persistence.GeneratedValue;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "email_code")
@Getter
@Setter
public class EmailCode {


    @Id
    @GeneratedValue
    private UUID id;

    private String email;

    private String code;

    private LocalDateTime sendingTime;

    private boolean used;


}
