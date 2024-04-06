package kz.teacher.forge.teacherforge.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Table(name = "work_time")
public class ReportWorkTime {
    @Id
    private UUID id;
    private UUID reportId;
    private String title;
    private String description;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate workDate;
    private UUID workedById;
}
