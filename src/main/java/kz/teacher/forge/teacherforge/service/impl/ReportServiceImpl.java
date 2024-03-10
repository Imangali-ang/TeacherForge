package kz.teacher.forge.teacherforge.service.impl;

import kz.teacher.forge.teacherforge.models.Report;
import kz.teacher.forge.teacherforge.models.dto.ReportDto;
import kz.teacher.forge.teacherforge.repository.ReportRepository;
import kz.teacher.forge.teacherforge.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
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
}
