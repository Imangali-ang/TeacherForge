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
    private String username;
    private List<String> roles;
    private String middlename;
    private String lastname;

    public JwtResponse(String token, UUID id, String username,String middlename , String lastname , List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.roles = roles;
        this.middlename =middlename;
        this.lastname = lastname;
    }
}
