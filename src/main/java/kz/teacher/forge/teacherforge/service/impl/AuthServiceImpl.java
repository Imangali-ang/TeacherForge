package kz.teacher.forge.teacherforge.service.impl;

import kz.teacher.forge.teacherforge.models.EmailCode;
import kz.teacher.forge.teacherforge.models.User;
import kz.teacher.forge.teacherforge.models.dto.Email;
import kz.teacher.forge.teacherforge.models.dto.ReceiveOpt;
import kz.teacher.forge.teacherforge.models.exception.ApiError;
import kz.teacher.forge.teacherforge.models.exception.ApiException;
import kz.teacher.forge.teacherforge.repository.EmailCodeRepository;
import kz.teacher.forge.teacherforge.repository.UserRepository;
import kz.teacher.forge.teacherforge.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final EmailCodeRepository emailCodeRepository;
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;
    private static final String ALLOWED_CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";


    @Override
    public void sendVerificationCode(Email email) {
        Optional<EmailCode> existCode = emailCodeRepository.findEmailCodeByEmail(email.getEmail());
        if(existCode.isPresent() &&
                Duration.between(existCode.get().getSendingTime(), LocalDateTime.now()).toMinutes() < 1) {
            throw new ApiException(ApiError.TOKEN_EXPIRED , "Can't send another one code to email, pls wat 1 minute");
        }
//        String verificationCode = generateRandomCode();
        String verificationCode = "00000";
        EmailCode emailCode = new EmailCode();
        try {
//            sendEmail(email.getEmail(), "Код подтверждения", "Ваш код подтверждения: " + verificationCode);
        } catch (Throwable ex){
            throw new ApiException(ApiError.BAD_REQUEST , "Can't send code to email");
        }
        emailCode.setUsed(false);
        emailCode.setCode(verificationCode);
        emailCode.setSendingTime(LocalDateTime.now());
        emailCode.setEmail(email.getEmail());
        emailCodeRepository.save(emailCode);
    }


    @Override
    public User getUserFromOpt(ReceiveOpt receiveOpt) {
        EmailCode emailCode = emailCodeRepository.findEmailCodeByEmail(receiveOpt.getEmail())
                .orElseThrow(()-> new ApiException(ApiError.FORBIDDEN , "can't find email code"));
        if(!emailCode.getCode().equals(receiveOpt.getCode())){
            throw new ApiException(ApiError.AUTHORIZATION_ERROR , "Email code not match");
        }
        if(Duration.between(emailCode.getSendingTime(), LocalDateTime.now()).toMinutes() > 5) {
            throw new ApiException(ApiError.TOKEN_EXPIRED , "Email code expired");
        }
        emailCode.setUsed(true);
        emailCodeRepository.save(emailCode);
        return userRepository.findByEmail(emailCode.getEmail())
                .orElseThrow(()-> new ApiException(ApiError.RESOURCE_NOT_FOUND , "Can't find user by email"));
    }

    protected void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

    protected String generateRandomCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder(6);

        for (int i = 0; i < 6; i++) {
            int randomIndex = random.nextInt(ALLOWED_CHARACTERS.length());
            char randomChar = ALLOWED_CHARACTERS.charAt(randomIndex);
            code.append(randomChar);
        }

        return code.toString();
    }



}
