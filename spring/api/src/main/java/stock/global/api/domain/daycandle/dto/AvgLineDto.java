package stock.global.api.domain.daycandle.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvgLineDto {
    @Schema(description="기준일 이후 캔들의 갯수")
    private Integer afterDay;
    @Schema(description="이동평균선 ex) 20 , 120 , 5")
    @Min(3)
    private Integer avgDay;
    @Schema(description="이동 평균선을 이탈한 범위 ex) 0.1 = 10% , -0.1 = -10%")
    @DecimalMax("2.0")
    @DecimalMin("-2.0")
    private Double range;
    @Schema(description="이동평균선 위 = 1 , 아래 = 2")
    @Min(1)
    @Max(2)
    private Integer upDown;
    @Schema(description="시가총액" , defaultValue="1000000")
    private Long marketValue;
    @Schema(hidden=true)
    private LocalDate date;

    @Schema(hidden=true)
    private List<Integer> after;

    @Schema(hidden=true)
    private List<Integer> before;

    public void setValues() {
        this.setdAfterDay();
        this.setBeforeDay();
        this.setDate();
    }


    public void setdAfterDay() {
        if(this.afterDay.equals(0)) return;
        this.after = new ArrayList<>(this.afterDay);
        for (int day = 1; day <= this.afterDay; day++) {
            this.after.add(day);
        }
    }

    public void setBeforeDay() {
        if(this.avgDay.equals(0))return;
        this.before = new ArrayList<>(this.avgDay);
        for (int day = 1; day <= this.avgDay; day++) {
            this.before.add(day);
        }
    }

    public void setDate() {
        this.date = LocalDate.now().minusDays(this.avgDay * 2);
    }
}
