package kz.teacher.forge.teacherforge.utils;

import kz.teacher.forge.teacherforge.models.Student;
import kz.teacher.forge.teacherforge.models.User;
import org.apache.commons.lang3.StringUtils;

public final class UserUtils {

    public static String getFullName(User user){
        StringBuilder fullName = new StringBuilder();
        if(StringUtils.isNotBlank(user.getUserName())) fullName.append(user.getUserName());
        if(StringUtils.isNotBlank(user.getLastName())) fullName.append(" " + user.getLastName());
        if(StringUtils.isNotBlank(user.getMiddleName())) fullName.append(" " + user.getMiddleName());
        return fullName.toString();
    }

    public static String getStudentsFullName(Student user){
        StringBuilder fullName = new StringBuilder();
        if(StringUtils.isNotBlank(user.getName())) fullName.append(user.getName());
        if(StringUtils.isNotBlank(user.getSurname())) fullName.append(" " + user.getSurname());
        if(StringUtils.isNotBlank(user.getMiddlename())) fullName.append(" " + user.getMiddlename());
        return fullName.toString();
    }
}
