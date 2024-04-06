package kz.teacher.forge.teacherforge.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kz.teacher.forge.teacherforge.models.Report;
import kz.teacher.forge.teacherforge.models.ReportType;
import kz.teacher.forge.teacherforge.models.Student;
import kz.teacher.forge.teacherforge.models.User;
import kz.teacher.forge.teacherforge.utils.UserUtils;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportDto {
    private UUID id;
    private UUID studentId;
    private String studentFullName;
    private String studentClass;
    private String studentPhoneNumber;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime violationTime;
    private Set<UUID> documentIds;
    private String lesson;
    private String place;
    private String comments;
    private UUID reportTypeId;
    private String reportTypeText;
    private Report.ReportStatus status;
    private UUID workedById;
    private String workedFullName;
    private LocalDateTime createdTime;
    private UUID createdById;
    private String createdFullName;
    private String teacherCategory;
    private String teacherPhoneNumber;
    public ReportDto(Report report) {
        this.id = report.getId();
        this.studentId = report.getStudentId();
        this.violationTime = report.getViolationTime();
        this.documentIds = report.getDocumentIds();
        this.lesson = report.getLesson();
        this.place = report.getPlace();
        this.comments = report.getComments();
        this.reportTypeId = report.getReportTypeId();
        this.status = report.getStatus();
        this.createdTime = report.getCreatedTime();
        this.createdById = report.getCreatedById();
    }

    public ReportDto(){

    }
}
