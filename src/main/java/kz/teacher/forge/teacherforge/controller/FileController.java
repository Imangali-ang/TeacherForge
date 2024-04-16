package kz.teacher.forge.teacherforge.controller;

import kz.teacher.forge.teacherforge.models.MinioFile;
import kz.teacher.forge.teacherforge.models.File;
import kz.teacher.forge.teacherforge.repository.FileRepository;
import kz.teacher.forge.teacherforge.service.StorageService;
import kz.teacher.forge.teacherforge.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final FileRepository fileRepository;
    private final StorageService storageService;
    @PostMapping
    @SneakyThrows
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
        fileDto.setImageData(ImageUtils.compressImage(file.getFile().getBytes()));
        fileDto = fileRepository.save(fileDto);
        fileDto.setName(fileDto.getId()+"." +file.getFile().getOriginalFilename()
                .substring(file.getFile().getOriginalFilename()
                        .lastIndexOf(".") + 1));
        return ResponseEntity.ok(fileRepository.save(fileDto));
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<?> getFile(@PathVariable("fileId") UUID fileId) {
        try {
            Optional<File> dbImageData = fileRepository.findById(fileId);
            byte[] images= ImageUtils.decompressImage(dbImageData.get().getImageData());
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf(MediaType.APPLICATION_OCTET_STREAM_VALUE)) // или другой MIME-тип в зависимости от файла
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbImageData.get().getName() + "\"") // Настройте имя файла
                    .body(images);
        } catch (Exception e) {
            // Обработка ошибки
            return ResponseEntity.internalServerError().build();
        }
    }

//    public static String encodeFilenameForContentDisposition(String filename) throws UnsupportedEncodingException {
//        // Replace spaces with "%20" and encode the rest of the characters that are non-ASCII
//        String encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8.toString()).replaceAll("\\+", "%20");
//
//        // Format the filename using RFC 5987 encoding
//        return String.format("attachment; filename*=UTF-8''%s", encodedFilename);
//    }
}
