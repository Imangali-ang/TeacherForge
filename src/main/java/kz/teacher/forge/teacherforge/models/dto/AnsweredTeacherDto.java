package kz.teacher.forge.teacherforge.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AnsweredTeacherDto {
    private String answeredFullName;
    private boolean answered;
    private UUID teacherId;
}
