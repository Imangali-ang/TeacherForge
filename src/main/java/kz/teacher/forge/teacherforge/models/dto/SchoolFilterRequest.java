package kz.teacher.forge.teacherforge.models.dto;

import kz.teacher.forge.teacherforge.models.School;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SchoolFilterRequest {
    private String name;
    private String regionId;
    private School.SchoolStatus status;
    private School.SchoolType type;
}
