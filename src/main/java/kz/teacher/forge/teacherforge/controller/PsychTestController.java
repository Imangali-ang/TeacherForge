package kz.teacher.forge.teacherforge.controller;

import kz.teacher.forge.teacherforge.models.Question;
import kz.teacher.forge.teacherforge.models.dto.TestDto;
import kz.teacher.forge.teacherforge.models.exception.ApiError;
import kz.teacher.forge.teacherforge.models.exception.ApiException;
import kz.teacher.forge.teacherforge.repository.QuestionRepository;
import kz.teacher.forge.teacherforge.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    private final QuestionRepository questionRepository;

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

    @GetMapping("/{testId}/question/{questionNum}")
    public ResponseEntity<Question> getQuestion(@PathVariable("testId") UUID testId ,
                                         @PathVariable("questionNum") int questionNum) {
       return  ResponseEntity.ok(questionRepository.findByTestIdAndNumber(testId , questionNum)
               .orElseThrow(()->new ApiException(ApiError.RESOURCE_NOT_FOUND , "Can't find test question")));
    }

    @PutMapping("/{testId}/question/{questionNum}")
    public Question editQuestion(@PathVariable("testId") UUID testId ,
                                 @PathVariable("questionNum") int questionNum,
                                  @RequestBody Question question){
        if(question.getId()==null){
            Question question1 = questionRepository.findByTestIdAndNumber(testId , questionNum)
                    .orElseThrow(()->new ApiException(ApiError.RESOURCE_NOT_FOUND , "Can't find test question"));
            question.setId(question1.getId());
            return questionRepository.save(question);
        }
        return questionRepository.save(question);
    }

    @DeleteMapping("/{testId}/question/{questionNum}")
    public ResponseEntity<?> deleteQuestion(@PathVariable("testId") UUID testId ,
                                            @PathVariable("questionNum") int questionNum) {
        try {
            Question question = questionRepository.findByTestIdAndNumber(testId , questionNum)
                    .orElseThrow(()->new ApiException(ApiError.RESOURCE_NOT_FOUND , "Can't find test question"));
            questionRepository.delete(question);
            return ResponseEntity.ok().build();
        } catch (Throwable throwable){
            throwable.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{testId}/finish")
    public ResponseEntity<?> finishCreating(@PathVariable("testId") UUID testId){
        testService.finishCreating(testId);
        return ResponseEntity.ok().build();
    }


}
