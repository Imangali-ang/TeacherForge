package kz.teacher.forge.teacherforge.controller.admin;

import kz.teacher.forge.teacherforge.models.Region;
import kz.teacher.forge.teacherforge.models.dto.StudentDto;
import kz.teacher.forge.teacherforge.models.exception.ApiError;
import kz.teacher.forge.teacherforge.models.exception.ApiException;
import kz.teacher.forge.teacherforge.mongo.models.Student;
import kz.teacher.forge.teacherforge.models.User;
import kz.teacher.forge.teacherforge.models.dto.UserDto;
import kz.teacher.forge.teacherforge.mongo.repository.StudentRepository;
import kz.teacher.forge.teacherforge.repository.RegionRepository;
import kz.teacher.forge.teacherforge.repository.UserRepository;
import lombok.AllArgsConstructor;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/admin/")
public class AdminController {
    private final String USER = "users";
    private final String SCHOOL = "schools";
    private final String SCHOOL_USERS = SCHOOL+"/{schoolId}/users";
    private final String STUDENTS = "students";
    private final String STUDENT = STUDENTS+"/{studentId}";
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

    @PostMapping(STUDENTS)
    public Student createStudent(@RequestBody StudentDto studentDto){
        log.info("Trying to create Student");
        Student student = new Student();
        student.setName(studentDto.getName());
        student.setSurname(studentDto.getSurname());
        student.setMiddlename(studentDto.getMiddlename());
        student.setPhotoId(studentDto.getPhotoId());
        student.setNationality(studentDto.getNationality());
        student.setPhoneNumber(studentDto.getPhoneNumber());
        student.setSchoolId(studentDto.getSchoolId());
        student.setGender(studentDto.getGender());
        student.setOrphan(studentDto.isOrphan());
        student.setBirthDate(studentDto.getBirthDate());
        student.setClassRoom(studentDto.getClassRoom());
        student.setEmail(studentDto.getEmail());
        student.setAdditionalProperties(studentDto.getAdditionalProperties());
        return studentRepository.save(student);
    }

    @GetMapping(STUDENTS)
    public List<Student> getStudentsListByParametrs(@RequestParam("field") String field) {
        return studentRepository.findByHealthInAdditionalFields(field);
    }

    @GetMapping(STUDENT)
    public Student getStudent(@PathVariable("studentId") String studentId){
        return studentRepository.findById(studentId).orElseThrow(()->new ApiException(ApiError.RESOURCE_NOT_FOUND , "not found student"));
    }

    @PutMapping (STUDENT)
    public Student editStudent(@PathVariable("studentId") String studentId , @RequestBody Student student){
        return studentRepository.save(student);
    }

    @DeleteMapping(STUDENT)
    public ResponseEntity<Object> deleteStudent(@PathVariable("studentId") String studentId){
        studentRepository.deleteById(studentId);
        return ResponseEntity.ok().build();
    }
}
