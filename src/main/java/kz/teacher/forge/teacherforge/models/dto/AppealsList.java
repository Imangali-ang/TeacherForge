package kz.teacher.forge.teacherforge.models.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class AppealsList {
    private List<UUID> appeals;
}
