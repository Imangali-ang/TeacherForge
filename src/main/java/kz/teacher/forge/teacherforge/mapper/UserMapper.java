package kz.teacher.forge.teacherforge.mapper;

import kz.teacher.forge.teacherforge.models.User;
import kz.teacher.forge.teacherforge.models.dto.UserFilterRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> getList(UserFilterRequest request);
}
