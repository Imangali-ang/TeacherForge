package kz.teacher.forge.teacherforge.models.dto;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class TestDto {
    private String title;
    private String description;
    private Set<UUID> teacherIds;
    private boolean sendAll;
}
