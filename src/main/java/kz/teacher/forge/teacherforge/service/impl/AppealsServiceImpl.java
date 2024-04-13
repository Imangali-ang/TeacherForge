package kz.teacher.forge.teacherforge.service.impl;

import kz.teacher.forge.teacherforge.models.Appeals;
import kz.teacher.forge.teacherforge.models.Region;
import kz.teacher.forge.teacherforge.models.School;
import kz.teacher.forge.teacherforge.models.User;
import kz.teacher.forge.teacherforge.models.dto.AppealsDto;
import kz.teacher.forge.teacherforge.models.exception.ApiError;
import kz.teacher.forge.teacherforge.models.exception.ApiException;
import kz.teacher.forge.teacherforge.repository.AppealsRepository;
import kz.teacher.forge.teacherforge.repository.RegionRepository;
import kz.teacher.forge.teacherforge.repository.SchoolRepository;
import kz.teacher.forge.teacherforge.repository.UserRepository;
import kz.teacher.forge.teacherforge.service.AppealsService;
import kz.teacher.forge.teacherforge.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppealsServiceImpl implements AppealsService {
    private final AppealsRepository appealsRepository;
    private final UserRepository userRepository;
    private final SchoolRepository schoolRepository;
    private final RegionRepository regionRepository;
    @Override
    public AppealsDto getAppeals(UUID id){
       Appeals appeals = appealsRepository.findById(id)
               .orElseThrow(()-> new ApiException(ApiError.RESOURCE_NOT_FOUND , "not found appels"));
       AppealsDto appealsDto = new AppealsDto();
       appealsDto.setId(id);
       appealsDto.setRead(appeals.isRead());
       appealsDto.setDeleted(appeals.isDeleted());
       appealsDto.setText(appeals.getText());
       appealsDto.setTopic(appeals.getTopic());
       appealsDto.setDocumentIds(appeals.getDocumentIds());
        User user = userRepository.findById(appeals.getCreatedBy()).get();
        School school = schoolRepository.findById(user.getSchoolId()).get();
        Region region = regionRepository.findById(school.getRegionId()).get();
       appealsDto.setCreatedFullName(UserUtils.getFullName(user));
       appealsDto.setSchoolName(school.getName());
       appealsDto.setSchoolAddress(region.getName()+","+school.getName());
       appealsDto.setCreated(appeals.getCreated());
       return appealsDto;
    }
}
