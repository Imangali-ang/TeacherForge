package kz.teacher.forge.teacherforge.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Table(name = "file")
public class File {
    @Id
    private UUID id;
    private String name;
    private Purpose purpose;
    private UUID uploadedById;
    private UUID receivedById;
    private LocalDateTime time;
    public enum Purpose{
        TEST,
        PHOTO
    }
}
