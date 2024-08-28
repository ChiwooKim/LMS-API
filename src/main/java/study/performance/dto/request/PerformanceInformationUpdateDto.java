package study.performance.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PerformanceInformationUpdateDto {

    public String performanceName;  //공연이름

    public String performanceNumber;  //공연 고유 번호

    public String performanceLocation;  //공연장소

    public String startDate;  //공연시작일

    public String endDate;  //공연종료일

    public String runtime;  //공연시간

    public String age; //관람연령

    public String price;  //가격

    public String thumbnail;  //썸네일

    @Builder
    public PerformanceInformationUpdateDto(String performanceName, String performanceNumber, String performanceLocation, String startDate, String endDate, String runtime, String age, String price, String thumbnail) {
        this.performanceName = performanceName;
        this.performanceNumber = performanceNumber;
        this.performanceLocation = performanceLocation;
        this.startDate = startDate;
        this.endDate = endDate;
        this.runtime = runtime;
        this.age = age;
        this.price = price;
        this.thumbnail = thumbnail;
    }
}
