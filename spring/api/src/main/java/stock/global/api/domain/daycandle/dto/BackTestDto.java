package stock.global.api.domain.daycandle.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BackTestDto {
    @Schema(hidden=true)
    protected LocalDate date;

    @Schema(description="기준일 이후 캔들의 갯수")
    protected Integer afterDay;
    
    @Schema(description="시가총액" , defaultValue="1000000")
    private Long marketValue;

    @Schema(hidden=true)
    private List<Integer> after;

    public void setAfterDay() {
        if(this.afterDay.equals(0)) return;
        this.after = new ArrayList<>(this.afterDay);
        for (int day = 1; day <= this.afterDay; day++) {
            this.after.add(day);
        }
    }
    
}
