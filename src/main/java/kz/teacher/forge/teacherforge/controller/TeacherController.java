package kz.teacher.forge.teacherforge.controller;

import kz.teacher.forge.teacherforge.models.Appeals;
import kz.teacher.forge.teacherforge.models.Report;
import kz.teacher.forge.teacherforge.models.Student;
import kz.teacher.forge.teacherforge.models.User;
import kz.teacher.forge.teacherforge.models.dto.ReportDto;
import kz.teacher.forge.teacherforge.repository.AppealsRepository;
import kz.teacher.forge.teacherforge.repository.ReportRepository;
import kz.teacher.forge.teacherforge.repository.ReportTypeRepository;
import kz.teacher.forge.teacherforge.repository.StudentRepository;
import kz.teacher.forge.teacherforge.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/client")
public class TeacherController {
    private final String TEACHER = "/{teacherId}";
    private final String REPORTS = "/reports";
    private final String TEACHER_REPORTS = TEACHER+REPORTS;
    private final ReportRepository reportRepository;
    private final ReportService reportService;
    private final ReportTypeRepository reportTypeRepository;
    private final StudentRepository studentRepository;
    private final SecurityService securityService;
    private final AppealsRepository appealsRepository;

    @PostMapping(TEACHER_REPORTS)
    public ResponseEntity<Object>  createReport(@PathVariable("teacherId") UUID teacherId,
                                                @RequestBody ReportDto reportDto){
        return ResponseEntity.ok(reportService.create(reportDto , teacherId));
    }

    @GetMapping("/reports-type")
    public ResponseEntity<Object> getReportsType(){
        return ResponseEntity.ok(reportTypeRepository.findAll());
    }

    @GetMapping("/students")
    public List<Student> seacrhStudent(@RequestParam("search") String text) {
        User teacher = securityService.getCurrentUser().get();
        return studentRepository.findByName(text, teacher.getSchoolId());
    }

}
