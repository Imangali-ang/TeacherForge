package kz.teacher.forge.teacherforge.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private UUID id;
    private List<String> roles;
    private String fullName;

    public JwtResponse(String token, UUID id, String fullName , List<String> roles) {
        this.token = token;
        this.id = id;
        this.roles = roles;
        this.fullName=fullName;
    }
}
