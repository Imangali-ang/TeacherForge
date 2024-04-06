package kz.teacher.forge.teacherforge.controller;

import jakarta.servlet.http.HttpServletResponse;
import kz.teacher.forge.teacherforge.mapper.ReportsMapper;
import kz.teacher.forge.teacherforge.models.CustomUserDetails;
import kz.teacher.forge.teacherforge.models.Report;
import kz.teacher.forge.teacherforge.models.ReportType;
import kz.teacher.forge.teacherforge.models.ReportWorkTime;
import kz.teacher.forge.teacherforge.models.Student;
import kz.teacher.forge.teacherforge.models.User;
import kz.teacher.forge.teacherforge.models.dto.ReportDto;
import kz.teacher.forge.teacherforge.models.dto.ReportsFilterRequest;
import kz.teacher.forge.teacherforge.models.exception.ApiError;
import kz.teacher.forge.teacherforge.models.exception.ApiException;
import kz.teacher.forge.teacherforge.repository.ReportRepository;
import kz.teacher.forge.teacherforge.repository.ReportTypeRepository;
import kz.teacher.forge.teacherforge.repository.StudentRepository;
import kz.teacher.forge.teacherforge.repository.UserRepository;
import kz.teacher.forge.teacherforge.repository.WorkTimeRepository;
import kz.teacher.forge.teacherforge.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/psychologist")
public class PsychController {

    private final ReportsMapper reportsMapper;
    private final ReportRepository reportRepository;
    private final WorkTimeRepository workTimeRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final ReportTypeRepository reportTypeRepository;

    @RequestMapping("/reports")
    public ResponseEntity<?> getReports(@RequestParam(name = "search", required = false) String text,
                                      @RequestParam(name = "status" , required = false , defaultValue = "IN_REQUEST") String status,
                                      @RequestParam(name = "page", defaultValue = "1") int page,
                                      @RequestParam(name = "sort" , required = false) String sort,
                                      @RequestParam(name = "pageSize", defaultValue = "30") int pageSize,
                                        HttpServletResponse response){
        ReportsFilterRequest request = ReportsFilterRequest.builder()
                .search(text)
                .size(pageSize)
                .page(page)
                .sort(sort)
                .status(status).build();
        List<Report> reports = reportsMapper.getList(request);
        List<ReportDto> reportDtos = new ArrayList<>();
        for(Report report: reports){
            ReportDto reportDto = new ReportDto(report);
            Optional<Student> student = studentRepository.findById(report.getStudentId());
            Optional<User> teacher = userRepository.findById(report.getCreatedById());
            Optional<User> psych;
            if(report.getWorkedById()!=null){
                psych = userRepository.findById(report.getWorkedById());
                psych.ifPresent(user -> reportDto.setWorkedFullName(UserUtils.getFullName(psych.get())));
            }
            student.ifPresent(value -> reportDto.setStudentFullName(UserUtils.getStudentsFullName(student.get())));
            teacher.ifPresent(user -> reportDto.setCreatedFullName(UserUtils.getFullName(user)));
            Optional<ReportType> reportType = reportTypeRepository.findById(report.getReportTypeId());
            reportType.ifPresent(type -> reportDto.setReportTypeText(type.getName()));
            reportDtos.add(reportDto);
        }
        response.addHeader("X-Total-Count", String.valueOf(reportsMapper.getCount(request)));
        return ResponseEntity.ok(reportDtos);
    }

    @PutMapping("/reports/{reportId}")
    public ResponseEntity<?> actions(@PathVariable("reportId") UUID id ,  @RequestParam("action") Report.ReportStatus action){
        Optional<Report> reportOpt = reportRepository.findById(id);
        if(!reportOpt.isPresent()){
            throw new ApiException(ApiError.RESOURCE_NOT_FOUND , "not found report");
        }
        Report report = reportOpt.get();
        UUID userId=null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) principal;
            userId = userDetails.getId();
            System.out.println("User ID: " + userId);
        } else {
            System.out.println("User ID cannot be found, principal is not an instance of UserDetails");
            throw new ApiException(ApiError.RESOURCE_NOT_FOUND , "not found user from context");
        }
        report.setWorkedById(userId);
        report.setStatus(action);
        return ResponseEntity.ok(reportRepository.save(report));
    }

    @GetMapping("/reports/{reportId}")
    public ResponseEntity<?> getReport(@PathVariable("reportId") UUID id) {
        Optional<Report> reportOpt = reportRepository.findById(id);
        if (!reportOpt.isPresent()) {
            throw new ApiException(ApiError.RESOURCE_NOT_FOUND, "not found report");
        }
        ReportDto reportDto = new ReportDto(reportOpt.get());
        Optional<Student> student = studentRepository.findById(reportDto.getStudentId());
        Optional<User> teacher = userRepository.findById(reportDto.getCreatedById());
        if(teacher.isPresent()) {
            reportDto.setCreatedFullName(UserUtils.getFullName(teacher.get()));
            reportDto.setTeacherCategory(teacher.get().getCategory());
            Optional.ofNullable(teacher.get().getPhoneNumber()).ifPresent(reportDto::setStudentPhoneNumber);
        }
        if(student.isPresent()){
            reportDto.setStudentFullName(UserUtils.getStudentsFullName(student.get()));
            reportDto.setStudentClass(student.get().getClassRoom());
            Optional.ofNullable(student.get().getPhoneNumber()).ifPresent(reportDto::setStudentPhoneNumber);
        }
        Optional<User> psych;
        if(reportDto.getWorkedById()!=null){
            psych = userRepository.findById(reportDto.getWorkedById());
            psych.ifPresent(user -> reportDto.setWorkedFullName(UserUtils.getFullName(psych.get())));
        }
        Optional<ReportType> reportType = reportTypeRepository.findById(reportDto.getReportTypeId());
        reportType.ifPresent(type -> reportDto.setReportTypeText(type.getName()));
        return ResponseEntity.ok(reportDto);
    }



    @GetMapping("/reports/{reportId}/work-times")
    public ResponseEntity<?> getWorkTimes(@PathVariable("reportId") UUID id) {
        Optional<Report> reportOpt = reportRepository.findById(id);
        if (!reportOpt.isPresent()) {
            throw new ApiException(ApiError.RESOURCE_NOT_FOUND, "not found report");
        }
        List<ReportWorkTime> reportWorkTimeList = workTimeRepository.findAllByReportId(reportOpt.get().getId());
        for(ReportWorkTime reportWorkTime: reportWorkTimeList){
            Optional<User> psych = userRepository.findById(reportWorkTime.getWorkedById());
            psych.ifPresent(user->reportWorkTime.setWorkedFullName(UserUtils.getFullName(user)));
        }
        return ResponseEntity.ok(reportWorkTimeList);
    }

    @PostMapping("/reports/{reportId}/work-times")
    public ResponseEntity<?> addWorkTime(@PathVariable("reportId") UUID id , @RequestBody ReportWorkTime workTime) {
        Optional<Report> reportOpt = reportRepository.findById(id);
        if (!reportOpt.isPresent()) {
            throw new ApiException(ApiError.RESOURCE_NOT_FOUND, "not found report");
        }
        if(workTime.getReportId()==null) workTime.setReportId(reportOpt.get().getId());
        UUID userId=null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) principal;
            userId = userDetails.getId();
            System.out.println("User ID: " + userId);
        } else {
            System.out.println("User ID cannot be found, principal is not an instance of UserDetails");
            throw new ApiException(ApiError.RESOURCE_NOT_FOUND , "not found user from context");
        }
        workTime.setWorkedById(userId);
        return ResponseEntity.ok(workTimeRepository.save(workTime));
    }

    @GetMapping("/students/{studentId}")
    public Student getStudent(@PathVariable("studentId") UUID studentId){
        return studentRepository.findById(studentId).orElseThrow(()->new ApiException(ApiError.RESOURCE_NOT_FOUND , "not found student"));
    }

    @GetMapping("/students/{studentId}/reports")
    public ResponseEntity<?> getStudentsReportHistory(@PathVariable("studentId") UUID studentId){
        Optional<Student> student = studentRepository.findById(studentId);
        if(!student.isPresent()){
            throw new ApiException(ApiError.RESOURCE_NOT_FOUND , "not found student");
        }
        List<Report> reports = reportRepository.getReportsByStudentId(studentId);
        List<ReportDto> reportDtos = new ArrayList<>();
        for(Report report: reports){
            ReportDto reportDto = new ReportDto(report);
            Optional<User> teacher = userRepository.findById(report.getCreatedById());
            Optional<User> psych;
            if(report.getWorkedById()!=null){
                psych = userRepository.findById(report.getWorkedById());
                psych.ifPresent(user -> reportDto.setWorkedFullName(UserUtils.getFullName(psych.get())));
            }
            student.ifPresent(value -> reportDto.setStudentFullName(UserUtils.getStudentsFullName(student.get())));
            teacher.ifPresent(user -> reportDto.setCreatedFullName(UserUtils.getFullName(user)));
            Optional<ReportType> reportType = reportTypeRepository.findById(report.getReportTypeId());
            reportType.ifPresent(type -> reportDto.setReportTypeText(type.getName()));
            reportDtos.add(reportDto);
        }
        return ResponseEntity.ok(reportDtos);
    }
}
