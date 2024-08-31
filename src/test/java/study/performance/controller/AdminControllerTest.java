package study.performance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import study.performance.domain.Performance;
import study.performance.dto.request.PerformanceInformationRequestDto;
import study.performance.dto.request.PerformanceInformationUpdateDto;
import study.performance.repository.PerformanceRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PerformanceRepository performanceRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void clean() {
        performanceRepository.deleteAll();
    }

    @Test
    @DisplayName("공연정보 등록")
    void test1() throws Exception {
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

        String json = objectMapper.writeValueAsString(requestDto);

        //expected
        mockMvc.perform(post("/admin/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("공연 정보 수정")
    void test2() throws Exception {
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

        String json = objectMapper.writeValueAsString(updateDto);

        //when
        mockMvc.perform(patch("/admin/{performanceId}", performance.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

        //then
        List<Performance> list = performanceRepository.findAll();
        assertThat(list.getFirst().getPerformanceName()).isNotEqualTo(name);
        assertThat(list.getFirst().getPerformanceName()).isEqualTo(updateName);
    }

    @Test
    @DisplayName("공연 정보 삭제")
    void test3() throws Exception {
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
        mockMvc.perform(delete("/admin/{performanceId}", performance.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());

        //then
        assertThat(performanceRepository.findById(performance.getId())).isEmpty();
    }

    @Test
    @DisplayName("1개의 영화 정보 조회")
    void test4() throws Exception {
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

        //expected
        mockMvc.perform(get("/admin/{performanceId}", performance.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.performanceName").value(performance.getPerformanceName()))
                .andDo(print());
    }
}