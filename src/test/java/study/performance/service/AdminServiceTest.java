package study.performance.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.performance.domain.Performance;
import study.performance.dto.request.PerformanceInformationRequestDto;
import study.performance.dto.request.PerformanceInformationUpdateDto;
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

    @Test
    @DisplayName("공연 정보 수정")
    void test2() {
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
        Performance performance = Performance.builder()
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
        performanceRepository.save(performance);

        String updateName = "뮤지컬 시카고 <창원>";
        PerformanceInformationUpdateDto updateDto = PerformanceInformationUpdateDto.builder()
                .performanceName(updateName)
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
        adminService.update(performance.getId(), updateDto);

        //then
        Performance updatePerformance = performanceRepository.findById(performance.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 공연이 존재하지 않습니다. id=" + performance.getId()));
        assertThat(updatePerformance.getPerformanceName()).isNotEqualTo(name);
        assertThat(updatePerformance.getPerformanceName()).isEqualTo(updateName);
    }

    @Test
    @DisplayName("공연 정보 삭제")
    void test3() {
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
        Performance performance = Performance.builder()
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
        performanceRepository.save(performance);

        //when
        adminService.delete(performance.getId());

        //then
        assertThat(performanceRepository.count()).isEqualTo(0);
    }
}