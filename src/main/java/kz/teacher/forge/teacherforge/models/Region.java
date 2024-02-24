package kz.teacher.forge.teacherforge.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@Table(name = "region")
public class Region {
    @Id
    private UUID id;
    private String name;
}
