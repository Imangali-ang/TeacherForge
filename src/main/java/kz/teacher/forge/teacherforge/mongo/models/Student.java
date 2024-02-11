package kz.teacher.forge.teacherforge.mongo.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@Document(collection = "students")
public class Student {
    @Id
    private String id;
    private String name;
    private String surname;
    private Map<String, Object> additionalProperties;
}