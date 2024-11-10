package stock.global.api.domain.daycandle.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvgLineDto extends BackTestDto {
    
    @Schema(description="이동평균선 ex) 20 , 120 , 5")
    @Min(3)
    private Integer avgDay;

    @Schema(
        description="이동평균선 위 = 1 , 아래 = 2" , 
        requiredMode=RequiredMode.REQUIRED
    )
    @Min(1)
    @Max(2)
    private int upDown;

    @Schema(
        description="이동 평균선 이탈한 범위 ex) 0.1 = 10% , -0.1 = -10%",
        requiredMode=RequiredMode.REQUIRED
    )
    @DecimalMax("2.0")
    @DecimalMin("-2.0")
    private Double range;

    @Schema(hidden=true)
    private List<Integer> before;

    public void setValues() {
        super.setAfterDay();
        this.setBeforeDay();
        this.setDate();
    }
    

    public void setBeforeDay() {
        if(this.avgDay.equals(0))return;
        this.before = new ArrayList<>(this.avgDay);
        for (int day = 1; day <= this.avgDay; day++) {
            this.before.add(day);
        }
    }

    public void setDate() {
        /**
         * 미개장일도 존재할 수 있어서 10일을 더합니다.
         */
        super.date = LocalDate.now().minusDays(this.avgDay * 2 + 10);
    }
}
