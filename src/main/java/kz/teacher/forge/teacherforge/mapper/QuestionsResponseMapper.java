package kz.teacher.forge.teacherforge.mapper;

import kz.teacher.forge.teacherforge.models.QuestionResponse;
import kz.teacher.forge.teacherforge.models.QuestionResponseFilter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuestionsResponseMapper {

    List<QuestionResponse> getList(QuestionResponseFilter filter);
}
