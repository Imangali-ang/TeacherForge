package kz.teacher.forge.teacherforge.models.dto;

import kz.teacher.forge.teacherforge.models.School;
import lombok.Data;

import java.util.UUID;

@Data
public class SchoolDto {
    private UUID id;
    private String domain;
    private String name;
    private School.SchoolStatus status;
    private School.SchoolType type;
    private UUID regionId;
    private String address;
}
