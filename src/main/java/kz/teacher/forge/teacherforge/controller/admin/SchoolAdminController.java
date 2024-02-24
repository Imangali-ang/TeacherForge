package kz.teacher.forge.teacherforge.controller.admin;

import kz.teacher.forge.teacherforge.models.School;
import kz.teacher.forge.teacherforge.models.dto.SchoolDto;
import kz.teacher.forge.teacherforge.models.dto.SchoolRequest;
import kz.teacher.forge.teacherforge.repository.SchoolRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/admin/schools")
public class SchoolAdminController {
    private final SchoolRepository schoolRepository;
    @PostMapping
    public School createSchool(@RequestBody SchoolDto schoolDto) {
        School school = new School(schoolDto);
        return schoolRepository.save(school);
    }

    @PutMapping
    @Transactional
    public School editSchool(@RequestBody SchoolDto schoolDto) {
        schoolRepository.deleteById(schoolDto.getId());
        School school = new School(schoolDto);
        return schoolRepository.save(school);
    }

    @GetMapping
    public List<School> getSchoolList(@RequestBody SchoolRequest request){
        return schoolRepository.filter(request.getName() ,
                request.getRegionId(),
                request.getStatus() ,
                request.getType());
    }

    @DeleteMapping("{schoolId}")
    public ResponseEntity<Object> getSchoolList(@RequestParam("schoolId") UUID schoolId){
        schoolRepository.deleteById(schoolId);
        return ResponseEntity.ok().build();
    }
}
