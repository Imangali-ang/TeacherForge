package kz.teacher.forge.teacherforge.models.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {

    private String code;
    private String description;
    private String origin;
    private LocalDateTime timestamp;
    private Map<String, Object> data;

    public ErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(String code, String description) {
        this.code = code.toLowerCase();
        this.description = description;
        this.timestamp = LocalDateTime.now();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code.toLowerCase();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, Object> getData() {
        if (data == null) {
            data = new HashMap<>();
        }
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

}
