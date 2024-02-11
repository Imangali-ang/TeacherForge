package kz.teacher.forge.teacherforge.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kz.teacher.forge.teacherforge.models.dto.SchoolDto;
import lombok.Data;

import java.util.UUID;

@Data
@Table(name="schools")
@Entity
public class School {
    @Id
    private UUID id;
    private String domain;
    private String name;
    private String number;

    public School(){}

    public School(SchoolDto schoolDto){
        domain = schoolDto.getDomain();
        name = schoolDto.getName();
        number = schoolDto.getName();
    }
}
