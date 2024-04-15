package kz.teacher.forge.teacherforge.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Table(name = "file")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class File {
    @Id
    private UUID id;
    private String name;
    private Purpose purpose;
    private UUID uploadedById;
    private UUID receivedById;
    @Column(value = "imagedata")
    private byte[] imageData;
    private LocalDateTime time;
    public enum Purpose{
        TEST,
        PHOTO
    }
}
