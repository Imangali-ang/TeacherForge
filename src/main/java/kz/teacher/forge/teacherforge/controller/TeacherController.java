package kz.teacher.forge.teacherforge.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/client/")
public class TeacherController {

    @GetMapping("hello")
    public String getHello(){
        return "Hello";
    }
}
