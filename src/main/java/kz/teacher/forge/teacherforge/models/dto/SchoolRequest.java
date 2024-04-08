package kz.teacher.forge.teacherforge.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kz.teacher.forge.teacherforge.models.School;
import lombok.Data;

import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SchoolRequest {
    private String name;
    private UUID regionId;
    private School.SchoolStatus status;
    private School.SchoolType type;
}
