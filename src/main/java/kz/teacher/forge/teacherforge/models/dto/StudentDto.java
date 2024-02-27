package kz.teacher.forge.teacherforge.models.dto;

import kz.teacher.forge.teacherforge.mongo.models.RoomClass;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Data
public class StudentDto {
    private String name;
    private String surname;
    private String middlename;
    private LocalDate birthDate;
    private String gender;
    private String nationality;
    private String email;
    private UUID photoId;
    private RoomClass roomClass;
    private boolean orphan;
    private Map<String , Object> additionalProperties;
}
