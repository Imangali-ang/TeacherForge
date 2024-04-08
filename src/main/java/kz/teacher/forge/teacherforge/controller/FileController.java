package kz.teacher.forge.teacherforge.controller;

import kz.teacher.forge.teacherforge.models.MinioFile;
import kz.teacher.forge.teacherforge.models.File;
import kz.teacher.forge.teacherforge.repository.FileRepository;
import kz.teacher.forge.teacherforge.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final FileRepository fileRepository;
    private final FileService fileService;
    @PostMapping
    @Transactional
    public ResponseEntity<File> uploadProfilePhoto(@RequestParam(value = "userId" , required = true) UUID uploaderId,
                                                   @RequestParam(value = "receiverId" , required = false) UUID receiverId,
                                                   @RequestParam(value = "purpose",required = true) File.Purpose purpose,
                                                   @ModelAttribute MinioFile file){
        File fileDto = new File();
        fileDto.setUploadedById(uploaderId);
        fileDto.setReceivedById(receiverId);
        fileDto.setTime(LocalDateTime.now());
        fileDto.setPurpose(purpose);
        String name = fileService.upload(file);
        fileDto.setName(name);
        return ResponseEntity.ok(fileRepository.save(fileDto));
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<Resource> getFile(@PathVariable("fileId") UUID fileId) {
        try {
            InputStream inputStream = fileService.getFile(fileId);
            InputStreamResource resource = new InputStreamResource(inputStream);
            File file = fileRepository.findById(fileId).get();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM) // или другой MIME-тип в зависимости от файла
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"") // Настройте имя файла
                    .body(resource);

        } catch (Exception e) {
            // Обработка ошибки
            return ResponseEntity.internalServerError().build();
        }
    }
}
