package kz.teacher.forge.teacherforge.models.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class AppealsDto {
    private UUID id;
    private String topic;
    private String text;
    private LocalDateTime created;
    private String createdFullName;
    private boolean isRead;
    private Set<UUID> documentIds;
    private String schoolName;
    private String schoolAddress;
    private boolean deleted;
}
