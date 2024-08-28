package study.performance.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import study.performance.dto.request.PerformanceInformationUpdateDto;
import study.performance.dto.request.PerformanceInformationRequestDto;
import study.performance.service.AdminService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/")
    public void postPerformanceInformation(@RequestBody @Valid PerformanceInformationRequestDto requestDto) {
        adminService.save(requestDto);
    }

    @PatchMapping("/{performanceId}")
    public void editPerformanceInformation(@PathVariable Long performanceId,
                                           @RequestBody @Valid PerformanceInformationUpdateDto requestDto) {
        adminService.update(performanceId, requestDto);
    }

    @DeleteMapping("/{performanceId}")
    public void delete(@PathVariable Long performanceId) {
        adminService.delete(performanceId);
    }
}
