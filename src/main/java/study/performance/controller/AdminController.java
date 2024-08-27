package study.performance.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
