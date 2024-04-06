package kz.teacher.forge.teacherforge.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.postgresql.util.PGobject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Configuration
public class JdbcConfiguration {

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
            return Arrays.stream(source.split(","))
                    .map(UUID::fromString)
                    .collect(Collectors.toSet());
        }
    }

    @WritingConverter
    public static class UUIDSetToStringConverter implements Converter<Set<UUID>, String> {
        @Override
        public String convert(Set<UUID> source) {
            return source.stream()
                    .map(UUID::toString)
                    .collect(Collectors.joining(","));
        }
    }
}
