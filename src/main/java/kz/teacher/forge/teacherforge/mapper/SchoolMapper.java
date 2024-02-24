package kz.teacher.forge.teacherforge.mapper;

import kz.teacher.forge.teacherforge.models.School;
import kz.teacher.forge.teacherforge.models.dto.SchoolFilterRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SchoolMapper {
    List<School> getList(SchoolFilterRequest request);
}
