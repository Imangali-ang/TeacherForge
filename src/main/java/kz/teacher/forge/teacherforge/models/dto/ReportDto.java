package kz.teacher.forge.teacherforge.models.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class ReportDto {
    private UUID studentId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime violationTime;
    private Set<UUID> documentIds;
    private String lesson;
    private String place;
    private String comments;
    private UUID reportTypeId;
}
