package kz.teacher.forge.teacherforge.mongo.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "rooms")
public class RoomClass {
    private Character letter;
    private int number;
}
