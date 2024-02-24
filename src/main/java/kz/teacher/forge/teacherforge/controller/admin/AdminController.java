package kz.teacher.forge.teacherforge.controller.admin;

import kz.teacher.forge.teacherforge.models.Region;
import kz.teacher.forge.teacherforge.models.School;
import kz.teacher.forge.teacherforge.models.dto.SchoolRequest;
import kz.teacher.forge.teacherforge.models.dto.StudentDto;
import kz.teacher.forge.teacherforge.mongo.models.Student;
import kz.teacher.forge.teacherforge.models.User;
import kz.teacher.forge.teacherforge.models.dto.SchoolDto;
import kz.teacher.forge.teacherforge.models.dto.UserDto;
import kz.teacher.forge.teacherforge.mongo.repository.StudentRepository;
import kz.teacher.forge.teacherforge.repository.RegionRepository;
import kz.teacher.forge.teacherforge.repository.SchoolRepository;
import kz.teacher.forge.teacherforge.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/admin/")
public class AdminController {
    private final String USER = "users";
    private final String SCHOOL = "schools";
    private final String STUDENT = "students";
    private final String REGION = "regions";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final StudentRepository studentRepository;
    private final RegionRepository regionRepository;

    @PostMapping(USER)
    public User createUser(@RequestBody UserDto userDto) {
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        User user = new User(userDto , encodedPassword);
        return userRepository.save(user);
    }



    @GetMapping(REGION)
    public List<Region> getRegions(){
        return regionRepository.findAll();
    }

    @PostMapping(STUDENT)
    public Student createStudent(@RequestBody StudentDto schoolDto){
        Student student = new Student();
        student.setName(schoolDto.getName());
        student.setSurname(schoolDto.getSurname());
        student.setAdditionalProperties(schoolDto.getAdditionalProperties());
        return studentRepository.save(student);
    }

    @GetMapping(STUDENT)
    public List<Student> getStudentsListByParametrs(@RequestParam("field") String field) {
        return studentRepository.findByHealthInAdditionalFields(field);
    }
}
