package kz.teacher.forge.teacherforge.controller;

import kz.teacher.forge.teacherforge.models.Test;
import kz.teacher.forge.teacherforge.models.dto.TestDto;
import kz.teacher.forge.teacherforge.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/psychologist/tests")
public class PsychTestController {

    private final TestRepository testRepository;

    @PostMapping
    public ResponseEntity<?> createTest(@RequestBody TestDto testDto){
        return ResponseEntity.ok(testRepository.save(new Test(testDto))); //TODO test
    }


}
