package kz.teacher.forge.teacherforge.config;

import kz.teacher.forge.teacherforge.models.User;
import kz.teacher.forge.teacherforge.models.dto.ReceiveOpt;
import kz.teacher.forge.teacherforge.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class OtpAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private AuthService authService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        ReceiveOpt otp = (ReceiveOpt) authentication.getPrincipal();
        User user = authService.getUserFromOpt(otp);

        if (user != null) {
            return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
        } else {
            throw new BadCredentialsException("Invalid OTP");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}