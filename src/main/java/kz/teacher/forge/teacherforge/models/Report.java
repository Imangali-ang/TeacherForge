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
    private UUID reportTypeId;
    @Transient
    private ReportType reportType;
    private String lesson;
    private LocalDateTime violationTime;
    private Set<UUID> documentIds;
    private String comments;
    private String place;
    private ReportStatus status;
    private UUID workedById;
    private LocalDateTime createdTime;
    private UUID createdById;

    public enum ReportStatus{
        IN_REQUEST,
        IN_WORK,
        FINISHED,
        CANCELLED
    }

}
