package kz.teacher.forge.teacherforge.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kz.teacher.forge.teacherforge.models.Test;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestDto {
    private UUID id;
    private String title;
    private String description;
    private Set<UUID> teacherIds;
    private boolean sendAll;
    private UUID createdId;
    private String createdFullName;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdTime;
    private String status;
    private String addressed;
    private Test.Status testStatus;
    private int questionCount;
    private UUID schoolId;

    public TestDto(){}

    public TestDto(Test test) {
        this.id=test.getId();
        title=test.getTitle();
        description=test.getDescription();
        createdTime=test.getCreatedTime();
        teacherIds=test.getTeacherIds();
        createdId=test.getCreatedId();
        this.addressed = test.getAddressed();
        this.status = test.getAnswered().size() +"/"+ test.getAddressedNum();
        testStatus=test.getStatus();
        questionCount=test.getQuestionCount();
        schoolId=test.getSchoolId();
    }
}
