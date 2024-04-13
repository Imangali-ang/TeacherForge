package kz.teacher.forge.teacherforge.service;

import kz.teacher.forge.teacherforge.models.dto.AppealsDto;

import java.util.UUID;

public interface AppealsService {
    AppealsDto getAppeals(UUID id);
}
