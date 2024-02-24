package kz.teacher.forge.teacherforge.models.dto;

import kz.teacher.forge.teacherforge.models.School;
import lombok.Data;

import java.util.UUID;

@Data
public class SchoolRequest {
    private String name;
    private UUID regionId;
    private School.SchoolStatus status;
    private School.SchoolType type;
}
