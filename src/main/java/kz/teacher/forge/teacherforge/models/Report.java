package kz.teacher.forge.teacherforge.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Table(name = "reports")
public class Report {
    @Id
    private UUID id;
    private UUID studentId;
    @Transient
    private String studentFullName;
    private UUID reportTypeId;
    @Transient
    private String reportTypeText;
    private String lesson;
    private LocalDateTime violationTime;
    private Set<UUID> documentIds;
    private String comments;
    private String place;
    private ReportStatus status;
    private UUID workedById;
    @Transient
    private String workedFullName;
    private LocalDateTime createdTime;
    private UUID createdById;
    @Transient
    private String createdFullName;

    public enum ReportStatus{
        IN_REQUEST,
        IN_WORK,
        FINISHED,
        CANCELLED
    }

}
