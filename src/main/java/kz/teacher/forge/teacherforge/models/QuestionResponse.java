package kz.teacher.forge.teacherforge.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@Table(name = "questions_responses")
public class QuestionResponse {
    @Id
    private UUID id;
    private UUID questionId;
    private UUID testId;
    private UUID teacherId;
    private int number;
    private QuestionType questionType;
    private String[] responses;
}
