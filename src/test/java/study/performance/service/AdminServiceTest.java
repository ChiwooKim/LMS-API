package study.performance.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.performance.domain.Performance;
import study.performance.dto.request.PerformanceInformationRequestDto;
import study.performance.repository.PerformanceRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PerformanceRepository performanceRepository;

    @AfterEach
    void clean() {
        performanceRepository.deleteAll();
    }

    @Test
    @DisplayName("공연정보 등록")
    void test1() {
        //given
        String name = "시카고";
        String number = "24011991";
        String location = "창원 성산아트홀 대극장";
        String startDate = "2024.10.25";
        String endDate = "2024.10.27";
        String runtime = "145분(인터미션 20분 포함)";
        String age = "14세 이상 관람가";
        String price = "VIP석 160,000원\n" + "R석 140,000원\n" + "S석 110,000원\n" + "A석 80,000원\n";
        String thumbnail = "//ticketimage.interpark.com/Play/image/large/24/24011991_p.gif";
        PerformanceInformationRequestDto requestDto = PerformanceInformationRequestDto.builder()
                .performanceName(name)
                .performanceNumber(number)
                .performanceLocation(location)
                .startDate(startDate)
                .endDate(endDate)
                .runtime(runtime)
                .age(age)
                .price(price)
                .thumbnail(thumbnail)
                .build();

        //when
        adminService.save(requestDto);

        //then
        assertThat(performanceRepository.count()).isEqualTo(1);

        Performance performance = performanceRepository.findAll().getFirst();
        assertThat(performance.getPerformanceName()).isEqualTo(name);
        assertThat(performance.getPerformanceNumber()).isEqualTo(number);
    }
}