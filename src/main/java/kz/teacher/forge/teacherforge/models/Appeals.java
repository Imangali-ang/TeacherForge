package kz.teacher.forge.teacherforge.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Table(name = "appeals")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Appeals {
    @Id
    private UUID id;
    @NonNull
    private String topic;
    @NonNull
    private String text;
    private LocalDateTime created;
    private UUID createdBy;
    private UUID schoolId;
    private boolean isRead;
    private Set<UUID> documentIds;
    private boolean deleted;
}
