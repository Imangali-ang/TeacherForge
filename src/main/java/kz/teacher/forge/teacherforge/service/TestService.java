package kz.teacher.forge.teacherforge.service;

import kz.teacher.forge.teacherforge.controller.SecurityService;
import kz.teacher.forge.teacherforge.models.Question;
import kz.teacher.forge.teacherforge.models.Test;
import kz.teacher.forge.teacherforge.models.User;
import kz.teacher.forge.teacherforge.models.dto.AnsweredTeacherDto;
import kz.teacher.forge.teacherforge.models.dto.QuestionDto;
import kz.teacher.forge.teacherforge.models.dto.TestDto;
import kz.teacher.forge.teacherforge.models.exception.ApiError;
import kz.teacher.forge.teacherforge.models.exception.ApiException;
import kz.teacher.forge.teacherforge.repository.QuestionRepository;
import kz.teacher.forge.teacherforge.repository.TestRepository;
import kz.teacher.forge.teacherforge.repository.UserRepository;
import kz.teacher.forge.teacherforge.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TestService {
    private final TestRepository testRepository;
    private final UserRepository userRepository;
    private final SecurityService securityService;
    private final EmailService emailService;
    private final QuestionRepository questionRepository;

    public Test createTest(TestDto testDto) {
        User user = securityService.getCurrentUser().get();
        testDto.setCreatedId(user.getId());
        testDto.setCreatedTime(LocalDateTime.now());
        Test test = new Test(testDto);
        int addressedNum = 0;
        if (testDto.isSendAll()){
            List<User> teachers = userRepository.getTeachersBySchoolId(user.getSchoolId());
            for (User teacher: teachers) {
                if(!testDto.getTeacherIds().contains(teacher.getId()) && StringUtils.isNotBlank(user.getEmail())) {
                    addressedNum+=1;
                    emailService.sendEmail(user.getEmail(), "Вам пришел тест" , "Пожалуйста, пройдите психологический тест на " + testDto.getTitle());
                }
            }
            if(testDto.getTeacherIds().isEmpty()) test.setAddressed("All school teachers");
            else test.setAddressed("All school teachers except " + testDto.getTeacherIds().size());
        } else {
            for(UUID teacherId: testDto.getTeacherIds()) {
                User teacher = userRepository.findById(teacherId)
                        .orElseThrow(()-> new ApiException(ApiError.RESOURCE_NOT_FOUND , "can't find teacher for sending test"));
                if(StringUtils.isNotBlank(teacher.getEmail())) {
                    addressedNum+=1;
                    emailService.sendEmail(teacher.getEmail(), "Вам пришел тест" , "Пожалуйста, пройдите психологический тест на " + testDto.getTitle());
                }
            }
            if(testDto.getTeacherIds().size()==1){
                for(UUID teacherId: testDto.getTeacherIds()) {
                    User teacher = userRepository.findById(teacherId)
                            .orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND, "can't find teacher for sending test"));
                    test.setAddressed(UserUtils.getFullName(teacher));
                }
            } else {
                test.setAddressed("Group of " + testDto.getTeacherIds().size());
            }
        }
        test.setAnswered(new HashSet<>());
        test.setAddressedNum(addressedNum);
        return testRepository.save(test);
    }

    public List<TestDto> search(String title) {
        List<Test> tests = testRepository.searchByTitleIsLike(title);
        List<TestDto> testDtoList = new ArrayList<>();
        for(Test test : tests) {
            TestDto testDto = new TestDto(test);
            Optional<User> user = userRepository.findById(test.getCreatedId());
            if (user.isPresent()) testDto.setCreatedFullName(UserUtils.getFullName(user.get()));
            testDtoList.add(testDto);
        }
        return testDtoList;
    }

    public List<AnsweredTeacherDto> findAnsweredTeachers(UUID testId){
        List<AnsweredTeacherDto> answeredTeacherDtos = new ArrayList<>();
        Test test = testRepository.findById(testId).orElseThrow(()-> new ApiException(ApiError.RESOURCE_NOT_FOUND , "not found tests"));
        if(test.isSendAll()) {
            User psych = userRepository.findById(test.getCreatedId()).get();
            List<User> teachers = userRepository.getTeachersBySchoolId(psych.getSchoolId());
            for(User teacher: teachers) {
                if(!test.getTeacherIds().contains(teacher.getId())) {
                    AnsweredTeacherDto answeredTeacherDto = new AnsweredTeacherDto();
                    answeredTeacherDto.setAnsweredFullName(UserUtils.getFullName(teacher));
                    if (test.getAnswered().contains(teacher.getId())) answeredTeacherDto.setAnswered(true);
                    else answeredTeacherDto.setAnswered(false);
                    answeredTeacherDtos.add(answeredTeacherDto);
                }
            }
        } else {
            for (UUID teacherId : test.getTeacherIds()) {
                User teacher = userRepository.findById(teacherId).get();
                AnsweredTeacherDto answeredTeacherDto = new AnsweredTeacherDto();
                answeredTeacherDto.setAnsweredFullName(UserUtils.getFullName(teacher));
                if (test.getAnswered().contains(teacher.getId())) answeredTeacherDto.setAnswered(true);
                else answeredTeacherDto.setAnswered(false);
                answeredTeacherDtos.add(answeredTeacherDto);
            }
        }
        return answeredTeacherDtos;
    }

    public Question createQuestion(UUID testId, Question question) {
        question.setTestId(testId);
        return questionRepository.save(question);
    }
}
