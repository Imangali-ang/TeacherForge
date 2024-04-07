package kz.teacher.forge.teacherforge.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Map;
import java.util.UUID;

@Data
@Table(name = "questions")
public class Question {
    @Id
    private UUID id;
    private UUID testId;
    private QuestionType questionType;
    private int number;
    private Map<String , Object> details;
}
