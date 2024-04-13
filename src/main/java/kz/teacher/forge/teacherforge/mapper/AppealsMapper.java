package kz.teacher.forge.teacherforge.mapper;

import kz.teacher.forge.teacherforge.models.Appeals;
import kz.teacher.forge.teacherforge.models.AppealsFilterRequest;
import kz.teacher.forge.teacherforge.models.Report;
import kz.teacher.forge.teacherforge.models.dto.ReportsFilterRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppealsMapper {
    List<Appeals> getList(AppealsFilterRequest request);
    Long getCount(AppealsFilterRequest filterRequest);
}
