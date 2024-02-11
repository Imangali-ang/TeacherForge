package kz.teacher.forge.teacherforge.models.dto;

import lombok.Data;

import java.util.Map;

@Data
public class StudentDto {
    private String name;
    private String surname;
    private Map<String , Object> additionalProperties;
}
