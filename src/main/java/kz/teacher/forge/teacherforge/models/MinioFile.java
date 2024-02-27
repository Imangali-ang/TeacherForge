package kz.teacher.forge.teacherforge.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class MinioFile {
    private MultipartFile file;
}
