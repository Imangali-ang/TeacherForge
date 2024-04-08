package kz.teacher.forge.teacherforge.models;

import kz.teacher.forge.teacherforge.models.dto.TestDto;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Table(name = "tests")
public class Test {
    @Id
    private UUID id;
    private String title;
    private String description;
    private Set<UUID> teacherIds;
    private boolean sendAll;
    private UUID createdId;
    private LocalDateTime createdTime;
    private String addressed;
    private int addressedNum;
    private Set<UUID> answered;
    private Status status;
    private int questionCount;

    public Test(){}

    public Test(TestDto testDto){
        createdId=testDto.getCreatedId();
        title=testDto.getTitle();
        description=testDto.getDescription();
        sendAll= testDto.isSendAll();
        teacherIds=testDto.getTeacherIds();
        createdTime = testDto.getCreatedTime();
    }

    public enum Status{
        DRAW,
        IN_PROGRESS,
        FINISHED
    }
}
