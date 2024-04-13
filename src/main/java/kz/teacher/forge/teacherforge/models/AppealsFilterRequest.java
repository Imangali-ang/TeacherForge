package kz.teacher.forge.teacherforge.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppealsFilterRequest {
    private String search;
    private int size;
    private int page;
    private boolean read;
}

