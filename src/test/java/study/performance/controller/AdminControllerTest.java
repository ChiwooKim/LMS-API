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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import study.performance.dto.request.PerformanceInformationRequestDto;
import study.performance.repository.PerformanceRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
        mockMvc.perform(MockMvcRequestBuilders.post("/admin/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }
}