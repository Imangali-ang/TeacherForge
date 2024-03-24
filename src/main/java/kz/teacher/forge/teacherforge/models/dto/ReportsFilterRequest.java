package kz.teacher.forge.teacherforge.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportsFilterRequest {
    private String search;
    private int size;
    private int page;
    private String status;
    private String sort;
}
