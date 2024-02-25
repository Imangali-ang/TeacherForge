package kz.teacher.forge.teacherforge.models;

import kz.teacher.forge.teacherforge.models.dto.SchoolDto;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@Table(name="schools")
public class School {
    @Id
    private UUID id;
    private String domain;
    private String name;
    @NonNull
    private UUID regionId;
    @NonNull
    private String address;
    private SchoolStatus status;
    private SchoolType type;

    public School(){}

    public School(SchoolDto schoolDto){
        domain = schoolDto.getDomain();
        name = schoolDto.getName();
        status = schoolDto.getStatus();
        type = schoolDto.getType();
        address = schoolDto.getAddress();
        regionId=schoolDto.getRegionId();
    }

    public enum SchoolStatus{
        STATE,
        MUNICIPAL,
        PRIVATE
    }

    public enum SchoolType{
        GYMNASIUMS,
        LYCEUMS,
        INTERNATIONAL,
        SPECIALIZED,
        GENERAL,
        BOARDING
    }
}
