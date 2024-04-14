package kz.teacher.forge.teacherforge.models.dto;

import kz.teacher.forge.teacherforge.models.QuestionType;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
public class QuestionsResponseDto {

    private UUID id;
    private String question;
    private Map<String , Object> details;
    private QuestionType questionType;
    private String[] responses;
    private int number;
}
