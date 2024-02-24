package kz.teacher.forge.teacherforge.controller;

import kz.teacher.forge.teacherforge.models.User;
import kz.teacher.forge.teacherforge.models.dto.Email;
import kz.teacher.forge.teacherforge.models.dto.JwtResponse;
import kz.teacher.forge.teacherforge.models.dto.ReceiveOpt;
import kz.teacher.forge.teacherforge.models.exception.ApiError;
import kz.teacher.forge.teacherforge.models.exception.ApiException;
import kz.teacher.forge.teacherforge.models.token.Token;
import kz.teacher.forge.teacherforge.repository.TokenRepository;
import kz.teacher.forge.teacherforge.repository.UserRepository;
import kz.teacher.forge.teacherforge.service.AuthService;
import kz.teacher.forge.teacherforge.utils.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final static String EMAIL_SEND = EMAIL + "/send";
    private final static String EMAIL_OTP = EMAIL + "/otp";

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping(EMAIL_SEND)
    public ResponseEntity<Object> sendMail(@RequestBody Email email) {
        User user = userRepository.findByEmail(email.getEmail().toLowerCase())
                .orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND , "Can't find by Email:" + email.getEmail()));
        if (!passwordEncoder.matches(email.getPassword(), user.getPassword())) {
            throw new ApiException(ApiError.AUTHORIZATION_ERROR, "Password is not correct");
        }
        authService.sendVerificationCode(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping(EMAIL_OTP)
    public ResponseEntity<?> receiveOptFromEmail(@RequestBody ReceiveOpt opt) {
        User user = authService.getUserFromOpt(opt);
        Authentication authentication = new UsernamePasswordAuthenticationToken(opt, null);
        authentication = authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt;
        if(opt.isRememberMe()) {
            jwt = jwtUtils.generateTokenWithoutExpiredDate(user.getEmail());
        } else {
            jwt = jwtUtils.generateToken(user.getEmail());
        }
        Token token = new Token();
        token.setToken(jwt);
        token.setUserId(user.getId());
        token.setTokenType(Token.TokenType.BEARER);
        tokenRepository.save(token);
        return ResponseEntity.ok(new JwtResponse(jwt,
                user.getId(),
                user.getUserName(),
                Arrays.asList(user.getUserRole().name())));
    }

}
