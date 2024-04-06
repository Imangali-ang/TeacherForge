package kz.teacher.forge.teacherforge.models;

import kz.teacher.forge.teacherforge.models.dto.TestDto;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

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

    public Test(TestDto testDto){
        title=testDto.getTitle();
        description=testDto.getDescription();
        sendAll= testDto.isSendAll();
        teacherIds=testDto.getTeacherIds();
    }
}
