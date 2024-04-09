package kz.teacher.forge.teacherforge.controller;

import kz.teacher.forge.teacherforge.models.Question;
import kz.teacher.forge.teacherforge.models.QuestionResponse;
import kz.teacher.forge.teacherforge.models.Test;
import kz.teacher.forge.teacherforge.models.User;
import kz.teacher.forge.teacherforge.models.exception.ApiError;
import kz.teacher.forge.teacherforge.models.exception.ApiException;
import kz.teacher.forge.teacherforge.repository.QuestionRepository;
import kz.teacher.forge.teacherforge.repository.QuestionResponseRepository;
import kz.teacher.forge.teacherforge.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/client/tests")
public class TeacherTestController {
    private final SecurityService securityService;
    private final TestService testService;
    private final QuestionRepository questionRepository;
    private final QuestionResponseRepository questionResponseRepository;
    @GetMapping
    public List<Test> getTests(){
        Optional<User> teacherOpt = securityService.getCurrentUser();
        if(!teacherOpt.isPresent()) {
            throw new ApiException(ApiError.AUTHORIZATION_ERROR , "Can't find teacher from Context");
        }
        User teacher = teacherOpt.get();
        List<Test> tests = testService.getTestsForTeacher(teacher);
        if(tests.isEmpty()) return Collections.emptyList();
        return testService.getTestsForTeacher(teacher);
    }

    @GetMapping("{testId}/questions/{questionNum}")
    public Question getQuestion(@PathVariable("testId") UUID testId ,
                                          @PathVariable("questionNum") int questionNum){
       return testService.getQuestion(testId , questionNum)
               .orElseThrow(()->new ApiException(ApiError.RESOURCE_NOT_FOUND , "Can't find Question"));
    }

    @PutMapping("{testId}/questions/{questionNum}")
    public QuestionResponse responseToQuestion(@PathVariable("testId") UUID testId ,
                                               @PathVariable("questionNum") int questionNum,
                                               @RequestBody QuestionResponse questionResponse) {
        Optional<QuestionResponse> response = questionResponseRepository.findByQuestId(questionResponse.getQuestionId());
        if(response.isPresent()) {
            QuestionResponse respon = response.get();
            respon.setResponses(questionResponse.getResponses());
            return questionResponseRepository.save(respon);
        }
        questionResponse.setTestId(testId);
        questionResponse.setNumber(questionNum);
        return questionResponseRepository.save(questionResponse);
    }

    @PutMapping("{testId}/finish")
    public ResponseEntity<?> finishTests(@PathVariable("testId") UUID testId){
        if(testService.finishTesting(testId)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
