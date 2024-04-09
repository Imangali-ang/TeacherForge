package kz.teacher.forge.teacherforge.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import org.apache.commons.lang3.StringUtils;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Configuration
@EnableJdbcRepositories(basePackages = "kz.teacher.forge.teacherforge.repository")
public class JdbcConfiguration {

    @Autowired
    DataSource dataSource;

    @Bean
    public NamedParameterJdbcOperations operations() {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    public static final ObjectMapper objectMapper;

    static
    {
        LocalDateTimeDeserializer localDateTimeDeserializer = new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME);
        LocalDateDeserializer localDateDeserializer = new LocalDateDeserializer(DateTimeFormatter.ISO_DATE);
        JavaTimeModule module = new JavaTimeModule();
        module.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);
        module.addDeserializer(LocalDate.class, localDateDeserializer);
        objectMapper = Jackson2ObjectMapperBuilder.json()
                .modules(module)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();
    }


    @Bean
    public JdbcCustomConversions jdbcCustomConversions() {
        return new JdbcCustomConversions(Arrays.asList(new MapToJsonConverter(),
                new JsonToMapConverter(),
                new StringToUUIDSetConverter(),
                new UUIDSetToStringConverter()));
    }

    @WritingConverter
    public static class MapToJsonConverter implements Converter<Map<String, Object>, PGobject> {

        private static final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public PGobject convert(Map<String, Object> source) {
            try {
                PGobject jsonObject = new PGobject();
                jsonObject.setType("jsonb");
                jsonObject.setValue(objectMapper.writeValueAsString(source));
                return jsonObject;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @ReadingConverter
    public static class JsonToMapConverter implements Converter<PGobject, Map<String, Object>> {

        private static final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public Map<String, Object> convert(PGobject source) {
            try {
                return objectMapper.readValue(source.getValue(), Map.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @ReadingConverter
    public static class StringToUUIDSetConverter implements Converter<String, Set<UUID>> {
        @Override
        public Set<UUID> convert(String source) {
            if(StringUtils.isBlank(source) || source.equals("{}")){
                return Collections.emptySet();
            }
            // Удаляем фигурные скобки и разделяем строку по запятым
            String[] uuids = source.replaceAll("[{}]", "").split(",");
            return Arrays.stream(uuids)
                    .map(String::trim) // Удаляем возможные пробелы вокруг UUID
                    .map(UUID::fromString)
                    .collect(Collectors.toSet());
        }
    }

    @WritingConverter
    public static class UUIDSetToStringConverter implements Converter<Set<UUID>, String> {
        @Override
        public String convert(Set<UUID> source) {
            if(source.isEmpty()) return "";
            return source.stream()
                    .map(UUID::toString)
                    .collect(Collectors.joining(","));
        }
    }
}
