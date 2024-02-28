package kz.teacher.forge.teacherforge.models.dto;

import kz.teacher.forge.teacherforge.mongo.models.RoomClass;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Data
public class StudentDto {
    private String name;
    private String surname;
    private String middlename;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;
    private String gender;
    private String nationality;
    private String email;
    private String phoneNumber;
    private UUID photoId;
    private String classRoom;
    private boolean orphan;
    private UUID schoolId;
    private Map<String , Object> additionalProperties;
}
