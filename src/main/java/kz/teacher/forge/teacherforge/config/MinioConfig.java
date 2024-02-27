package kz.teacher.forge.teacherforge.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint("http://localhost:9000") // Используйте ваш URL MinIO
                .credentials("minioadmin", "minioadmin") // Используйте ваши учетные данные
                .build();
    }
}