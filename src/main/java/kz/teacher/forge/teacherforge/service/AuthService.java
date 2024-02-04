package kz.teacher.forge.teacherforge.service;


import kz.teacher.forge.teacherforge.models.User;
import kz.teacher.forge.teacherforge.models.dto.Email;
import kz.teacher.forge.teacherforge.models.dto.ReceiveOpt;

public interface AuthService {

    void sendVerificationCode(Email email);

    User getUserFromOpt(ReceiveOpt receiveOpt);
}
