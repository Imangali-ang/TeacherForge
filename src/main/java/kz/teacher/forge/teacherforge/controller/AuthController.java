package kz.teacher.forge.teacherforge.controller;

import jakarta.servlet.http.HttpServletRequest;
import kz.teacher.forge.teacherforge.models.User;
import kz.teacher.forge.teacherforge.models.dto.Email;
import kz.teacher.forge.teacherforge.models.dto.JwtResponse;
import kz.teacher.forge.teacherforge.models.dto.ReceiveOpt;
import kz.teacher.forge.teacherforge.service.AuthService;
import kz.teacher.forge.teacherforge.utils.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth/")
public class AuthController {

    private final static String EMAIL = "email";
    private final static String EMAIL_SEND = EMAIL+"/send";
    private final static String EMAIL_OTP = EMAIL+"/otp";
    private final static String LOGOUT = "/logout";


    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @GetMapping(EMAIL_SEND)
    public void sendMail(@RequestBody Email email){
        authService.sendVerificationCode(email);
    }

    @PostMapping(EMAIL_OTP)
    public ResponseEntity<?> receiveOptFromEmail(@RequestBody ReceiveOpt opt){
        User user = authService.getUserFromOpt(opt);
        Authentication authentication = new UsernamePasswordAuthenticationToken(opt, null);
        authentication = authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateToken(user.getEmail());

        return ResponseEntity.ok(new JwtResponse(jwt,
                user.getId(),
                user.getUserName(),
                Arrays.asList(user.getUserRole().name())));
    }

    @PostMapping(LOGOUT)
    public ResponseEntity<?> logoutUser(HttpServletRequest request) {
        SecurityContextHolder.getContext().setAuthentication(null);
        return ResponseEntity.ok("Logged out successfully");
    }

}
