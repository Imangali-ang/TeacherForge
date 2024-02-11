package kz.teacher.forge.teacherforge;

import kz.teacher.forge.teacherforge.exception.ApiExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class TeacherForgeApplication {

    @Value("${spring.application.name}")
    private String applicationName;

    public static void main(String[] args) {
        SpringApplication.run(TeacherForgeApplication.class, args);
    }

    @Bean
    public ApiExceptionHandler exceptionHandler() {
        return new ApiExceptionHandler(applicationName);
    }

}
