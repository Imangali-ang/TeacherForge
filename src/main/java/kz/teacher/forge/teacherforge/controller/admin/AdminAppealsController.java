package kz.teacher.forge.teacherforge.controller.admin;

import jakarta.servlet.http.HttpServletResponse;
import kz.teacher.forge.teacherforge.mapper.AppealsMapper;
import kz.teacher.forge.teacherforge.models.AppealsFilterRequest;
import kz.teacher.forge.teacherforge.models.dto.AppealsDto;
import kz.teacher.forge.teacherforge.models.dto.AppealsList;
import kz.teacher.forge.teacherforge.repository.AppealsRepository;
import kz.teacher.forge.teacherforge.service.AppealsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/appeals")
public class AdminAppealsController {
    private final AppealsService appealsService;
    private final AppealsRepository appealsRepository;
    private final AppealsMapper appealsMapper;
    @GetMapping("/{appealsId}")
    public ResponseEntity<AppealsDto> getAppeals(@PathVariable("appealsId") UUID id){
        AppealsDto appealsDto = appealsService.getAppeals(id);
        appealsRepository.markAsRead(id);
        return ResponseEntity.ok(appealsDto);
    }

    @DeleteMapping("/{appealsId}")
    public ResponseEntity<?> deleteAppeal(@PathVariable("appealsId") UUID id){
        try {
            appealsRepository.markAsDeleted(id);
            return ResponseEntity.ok().build();
        } catch (Throwable ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAppeals(@RequestBody AppealsList appealsId) {
        try {
            for(UUID id: appealsId.getAppeals()) {
                appealsRepository.markAsDeleted(id);
            }
            return ResponseEntity.ok().build();
        } catch (Throwable ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public List<AppealsDto> getAppeals(@RequestParam(name = "search", required = false) String text,
                                       @RequestParam(name = "page", defaultValue = "1") int page,
                                       @RequestParam(name = "read" , required = false) boolean read,
                                       @RequestParam(name = "pageSize", defaultValue = "30") int pageSize,
                                       HttpServletResponse response){
        AppealsFilterRequest appealsFilterRequest = AppealsFilterRequest.builder()
                .search(text)
                .size(pageSize)
                .read(read)
                .page(page).build();
        response.addHeader("X-Total-Count", String.valueOf(appealsMapper.getCount(appealsFilterRequest)));
        return appealsMapper.getList(appealsFilterRequest).stream().map(appeals -> appealsService.getAppeals(appeals.getId())).collect(Collectors.toList());
    }


}
