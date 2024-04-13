package kz.teacher.forge.teacherforge.controller;

import kz.teacher.forge.teacherforge.models.Appeals;
import kz.teacher.forge.teacherforge.models.User;
import kz.teacher.forge.teacherforge.models.dto.AppealsDto;
import kz.teacher.forge.teacherforge.repository.AppealsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/client/appeals")
public class TeacherAppealsController {
    private final SecurityService securityService;
    private final AppealsRepository appealsRepository;
    @PostMapping
    public ResponseEntity<Appeals> create(@RequestBody Appeals appeals) {
        if(appeals.getCreatedBy()==null){
            User user = securityService.getCurrentUser().get();
            appeals.setCreatedBy(user.getId());
            appeals.setSchoolId(user.getSchoolId());
        }
        appeals.setCreated(LocalDateTime.now());
        appeals.setDeleted(false);
        appeals.setRead(false);
        return ResponseEntity.ok(appealsRepository.save(appeals));
    }
}
