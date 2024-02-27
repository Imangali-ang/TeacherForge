package kz.teacher.forge.teacherforge.service;

import com.google.common.io.ByteStreams;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import kz.teacher.forge.teacherforge.models.File;
import kz.teacher.forge.teacherforge.models.MinioFile;
import kz.teacher.forge.teacherforge.models.exception.ApiError;
import kz.teacher.forge.teacherforge.models.exception.ApiException;
import kz.teacher.forge.teacherforge.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

    private final MinioClient minioClient;
    private final String bucket = "teacher-forge";

    @Transactional
    public String upload(
            final MinioFile image
    ) {
        try {
            createBucket();
        } catch (Exception e) {
            throw new ApiException(ApiError.INTERNAL_SERVER_ERROR ,"Image must have name.");
        }
        MultipartFile file = image.getFile();
        if (file.isEmpty() || file.getOriginalFilename() == null) {
            throw new ApiException(ApiError.INTERNAL_SERVER_ERROR ,"Image must have name.");
        }
        String fileName = generateFileName(file);
        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
        } catch (Exception e) {
            throw new ApiException(ApiError.INTERNAL_SERVER_ERROR , "can't save file");
        }
        saveImage(inputStream, fileName);
        return fileName;
    }

    @SneakyThrows
    private void createBucket() {
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket(bucket)
                .build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucket)
                    .build());
        }
    }

    private String generateFileName(
            final MultipartFile file
    ) {
        String extension = getExtension(file);
        return UUID.randomUUID() + "." + extension;
    }

    private String getExtension(
            final MultipartFile file
    ) {
        return file.getOriginalFilename()
                .substring(file.getOriginalFilename()
                        .lastIndexOf(".") + 1);
    }

    @SneakyThrows
    private void saveImage(
            final InputStream inputStream,
            final String fileName
    ) {
        minioClient.putObject(PutObjectArgs.builder()
                .stream(inputStream, inputStream.available(), -1)
                .bucket(bucket)
                .object(fileName)
                .build());
    }

    @SneakyThrows
    public InputStream getFile(UUID id) {
        Optional<File> file = fileRepository.findById(id);
        if(!file.isPresent()){
            throw new ApiException(ApiError.RESOURCE_NOT_FOUND, "file not found");
        }
        try {
            InputStream stream = minioClient.getObject(
                    GetObjectArgs.builder().bucket(bucket).object(file.get().getName()).build());
            return stream;
        } catch (MinioException | NoSuchAlgorithmException | IOException | InvalidKeyException e) {
            throw new ApiException(ApiError.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}

