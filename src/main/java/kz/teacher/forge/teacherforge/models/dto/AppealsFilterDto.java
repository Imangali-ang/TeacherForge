package kz.teacher.forge.teacherforge.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppealsFilterDto {
    private String topic;
    private boolean isRead;
//    private
}
