package kz.teacher.forge.teacherforge.mapper;

import kz.teacher.forge.teacherforge.models.Report;
import kz.teacher.forge.teacherforge.models.dto.ReportsFilterRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReportsMapper {
    List<Report> getList(ReportsFilterRequest request);
    Long getCount(ReportsFilterRequest filterRequest);
}
