package kz.teacher.forge.teacherforge.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.UUID;

@Data
@Table("reports_type")
public class ReportType implements Serializable {
    @Id
    private UUID id;
    private String name;
    private String description;
}
