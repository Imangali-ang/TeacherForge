package kz.teacher.forge.teacherforge.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Data
@Table(name = "students")
public class Student {
    @Id
    private UUID id;
    private String name;
    private String surname;
    private String middlename;
    private UUID photoId;
    private String phoneNumber;
    private LocalDate birthDate;
    private String gender;
    private String nationality;
    private String email;
    private String classRoom;
    private boolean orphan;
    private UUID schoolId;
    private Map<String , Object> additionalProperties;
}
