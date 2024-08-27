package study.performance.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.performance.dto.request.PerformanceInformationRequestDto;
import study.performance.repository.PerformanceRepository;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final PerformanceRepository performanceRepository;

    @Transactional
    public void save(PerformanceInformationRequestDto requestDto) {
        performanceRepository.save(requestDto.toEntity());
    }
}
