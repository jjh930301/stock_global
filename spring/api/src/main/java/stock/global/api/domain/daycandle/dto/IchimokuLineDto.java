package stock.global.api.domain.daycandle.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IchimokuLineDto {
    @Schema(
        description="""
            전환선 = 1
            기준선 = 2
            선행스팬1 = 3
            선행스팬2 = 4
            """,
        requiredMode=RequiredMode.REQUIRED
    )
    @Min(1)
    @Max(4)
    private int line;
    @Schema(
        description="라인 위 = 1 , 아래 = 2" , 
        requiredMode=RequiredMode.REQUIRED
    )
    @Min(1)
    @Max(2)
    private int upDown;

    @Schema(
        description="특정선이나 스팬을 종가가 이탈한 범위 ex) 0.1 = 10% , -0.1 = -10%",
        requiredMode=RequiredMode.NOT_REQUIRED
    )
    private Double range;
}
