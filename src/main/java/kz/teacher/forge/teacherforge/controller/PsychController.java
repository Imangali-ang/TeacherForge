package kz.teacher.forge.teacherforge.controller;

import kz.teacher.forge.teacherforge.mapper.ReportsMapper;
import kz.teacher.forge.teacherforge.models.CustomUserDetails;
import kz.teacher.forge.teacherforge.models.Report;
import kz.teacher.forge.teacherforge.models.User;
import kz.teacher.forge.teacherforge.models.dto.ReportsFilterRequest;
import kz.teacher.forge.teacherforge.models.exception.ApiError;
import kz.teacher.forge.teacherforge.models.exception.ApiException;
import kz.teacher.forge.teacherforge.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static kz.teacher.forge.teacherforge.models.Report.ReportStatus.IN_REQUEST;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/psychologist")
public class PsychController {

    private final ReportsMapper reportsMapper;
    private final ReportRepository reportRepository;

    @RequestMapping("/reports")
    public List<Report> getReports(@RequestParam(name = "search", required = false) String text,
                                   @RequestParam(name = "status" , required = false , defaultValue = "IN_REQUEST") String status,
                                   @RequestParam(name = "page", defaultValue = "1") int page,
                                   @RequestParam(name = "sort" , required = false) String sort,
                                   @RequestParam(name = "pageSize", defaultValue = "30") int pageSize){
        ReportsFilterRequest request = ReportsFilterRequest.builder()
                .search(text)
                .size(pageSize)
                .page(page)
                .sort(sort)
                .status(status).build();
        List<Report> reports = reportsMapper.getList(request);
        return reports;
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
}
