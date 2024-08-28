package study.performance.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Performance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    public String performanceName;  //공연이름

    @Column(nullable = false)
    public String performanceNumber;  //공연 고유 번호

    @Column(nullable = false)
    public String performanceLocation;  //공연장소

    @Column(nullable = false)
    public String startDate;  //공연시작일

    @Column(nullable = false)
    public String endDate;  //공연종료일(1일 공연은 시작일과 동일하게 작성, 오픈런은 오픈런으로 작성)

    @Column(nullable = false)
    public String runtime;  //공연시간

    @Column(nullable = false)
    public String age; //관람연령

    @Column(nullable = false)
    public String price;  //가격

    @Column(nullable = false)
    public String thumbnail;  //썸네일

    @Builder
    public Performance(String performanceName, String performanceNumber, String performanceLocation, String startDate,
                       String endDate, String runtime, String age, String price, String thumbnail) {
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

    public void update(String performanceName, String performanceNumber, String performanceLocation, String startDate,
                       String endDate, String runtime, String age, String price, String thumbnail) {
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
