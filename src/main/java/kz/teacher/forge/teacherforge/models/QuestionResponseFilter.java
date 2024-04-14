package kz.teacher.forge.teacherforge.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponseFilter {
    private int size;
    private int page;
    private UUID testId;
    private UUID teacherId;
}
