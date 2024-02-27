package kz.teacher.forge.teacherforge.mongo.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Data
@Document(collection = "students")
public class Student {
    @Id
    private String id;
    private String name;
    private String surname;
    private String middlename;
    private UUID photoId;
    private String phoneNumber;
    private LocalDate birthDate;
    private String gender;
    private String nationality;
    private String email;
    private RoomClass roomClass;
    private boolean orphan;
    private Map<String, Object> additionalProperties;
}