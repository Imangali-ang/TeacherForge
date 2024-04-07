package kz.teacher.forge.teacherforge.controller;

import kz.teacher.forge.teacherforge.models.Question;
import kz.teacher.forge.teacherforge.models.dto.QuestionDto;
import kz.teacher.forge.teacherforge.models.dto.TestDto;
import kz.teacher.forge.teacherforge.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/psychologist/tests")
public class PsychTestController {
    private final TestService testService;

    @PostMapping
    public ResponseEntity<?> createTest(@RequestBody TestDto testDto){
        return ResponseEntity.ok(testService.createTest(testDto));
    }

    @GetMapping
    public ResponseEntity<?> searchTests(@RequestParam("search") String title) {
        return ResponseEntity.ok(testService.search(title));
    }

    @GetMapping("/{testId}")
    public ResponseEntity<?> getTestAnswered(@PathVariable("testId") UUID testId) {
        return ResponseEntity.ok(testService.findAnsweredTeachers(testId));
    }

    @PostMapping("/{testId}/question")
    public ResponseEntity<?> createQuestion(@PathVariable("testId") UUID testId,
                                            @RequestBody Question questionDto){
        return ResponseEntity.ok(testService.createQuestion(testId , questionDto));
    }

}
