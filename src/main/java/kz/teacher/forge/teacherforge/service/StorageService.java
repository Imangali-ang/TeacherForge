package kz.teacher.forge.teacherforge.service;

import kz.teacher.forge.teacherforge.models.File;
import kz.teacher.forge.teacherforge.models.MinioFile;
import kz.teacher.forge.teacherforge.repository.FileRepository;
import kz.teacher.forge.teacherforge.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class StorageService {

    private final FileRepository repository;

    public File uploadImage(MinioFile file) throws IOException {
        File imageData = repository.save(File.builder()
                .name(file.getFile().getOriginalFilename())
                .imageData(ImageUtils.compressImage(file.getFile().getBytes())).build());
        if (imageData != null) {
            log.info("succesfully saved");
        }
        return imageData;
    }

    public byte[] downloadImage(UUID id){
        Optional<File> dbImageData = repository.findById(id);
        byte[] images=ImageUtils.decompressImage(dbImageData.get().getImageData());
        return images;
    }
}
