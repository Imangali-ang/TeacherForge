package kz.teacher.forge.teacherforge.service.impl;

import kz.teacher.forge.teacherforge.controller.SecurityService;
import kz.teacher.forge.teacherforge.models.Report;
import kz.teacher.forge.teacherforge.models.User;
import kz.teacher.forge.teacherforge.models.dto.ReportDto;
import kz.teacher.forge.teacherforge.models.exception.ApiError;
import kz.teacher.forge.teacherforge.models.exception.ApiException;
import kz.teacher.forge.teacherforge.repository.ReportRepository;
import kz.teacher.forge.teacherforge.repository.ReportTypeRepository;
import kz.teacher.forge.teacherforge.repository.StudentRepository;
import kz.teacher.forge.teacherforge.repository.UserRepository;
import kz.teacher.forge.teacherforge.service.ReportService;
import kz.teacher.forge.teacherforge.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final SecurityService securityService;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final ReportTypeRepository reportTypeRepository;
    @Override
    public Report create(ReportDto reportDto , UUID teacherId) {
        Report report = new Report();
        report.setCreatedById(teacherId);
        report.setCreatedTime(LocalDateTime.now());
        report.setPlace(reportDto.getPlace());
        report.setComments(reportDto.getComments());
        report.setReportTypeId(reportDto.getReportTypeId());
        report.setStudentId(reportDto.getStudentId());
        report.setDocumentIds(reportDto.getDocumentIds());
        report.setLesson(reportDto.getLesson());
        if(reportDto.getViolationTime()!=null) {
            report.setViolationTime(reportDto.getViolationTime());
        } else {
            report.setViolationTime(LocalDateTime.now());
        }
        report.setStatus(Report.ReportStatus.IN_REQUEST);
        return reportRepository.save(report);
    }

    @Override
    public ReportDto updateReportStatus(UUID reportId, Report.ReportStatus action) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ApiException(ApiError.RESOURCE_NOT_FOUND, "Report not found"));
        User user = securityService.getCurrentUser()
                .orElseThrow(()-> new ApiException(ApiError.INTERNAL_SERVER_ERROR , "not found user"));
        report.setWorkedById(user.getId());
        report.setStatus(action);
        reportRepository.save(report);
        return buildReportDto(report);
    }

    @Override
    public ReportDto buildReportDto(Report report) {
        ReportDto reportDto = new ReportDto(report);
        if(report.getWorkedById()!=null) {
            User user = userRepository.findById(report.getWorkedById())
                    .orElseThrow(()-> new ApiException(ApiError.RESOURCE_NOT_FOUND , "user not found"));
            reportDto.setWorkedFullName(UserUtils.getFullName(user));
        }
        Optional.ofNullable(userRepository.findById(reportDto.getCreatedById()))
                .ifPresent(user -> {
                    reportDto.setCreatedFullName(UserUtils.getFullName(user.get()));
                    reportDto.setTeacherCategory(user.get().getCategory());
                    String teacherPhone = user.get().getPhoneNumber();
                    if (teacherPhone != null) reportDto.setTeacherPhoneNumber(teacherPhone);
                });

        studentRepository.findById(reportDto.getStudentId())
                .ifPresent(student -> {
                    reportDto.setStudentFullName(UserUtils.getStudentsFullName(student));
                    reportDto.setStudentClass(student.getClassRoom());
                    String studentPhone = student.getPhoneNumber();
                    if (studentPhone != null) reportDto.setStudentPhoneNumber(studentPhone);
                });

        userRepository.findById(reportDto.getWorkedById())
                .ifPresent(user -> reportDto.setWorkedFullName(UserUtils.getFullName(user)));

        reportTypeRepository.findById(reportDto.getReportTypeId())
                .ifPresent(type -> reportDto.setReportTypeText(type.getName()));

        return reportDto;
    }
}
