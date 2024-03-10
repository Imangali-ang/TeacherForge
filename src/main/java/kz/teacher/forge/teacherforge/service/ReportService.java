package kz.teacher.forge.teacherforge.service;

import kz.teacher.forge.teacherforge.models.Report;
import kz.teacher.forge.teacherforge.models.dto.ReportDto;

import java.util.UUID;

public interface ReportService {
    Report create(ReportDto reportDto , UUID teacherId);
}
