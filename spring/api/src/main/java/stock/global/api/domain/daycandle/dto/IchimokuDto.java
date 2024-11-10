package stock.global.api.domain.daycandle.dto;

import java.time.LocalDate;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IchimokuDto extends BackTestDto{
    @Schema(
        description="전환선",
        defaultValue="9",
        requiredMode=RequiredMode.NOT_REQUIRED
    )
    private Integer transitionLine = 9;

    @Schema(
        description="기준선",
        defaultValue="26",
        requiredMode=RequiredMode.NOT_REQUIRED
    )
    private Integer baseLine = 26;

    @Schema(
        description="선행스팬2",
        defaultValue="52",
        requiredMode=RequiredMode.NOT_REQUIRED
    )
    private Integer precedSpan2 = 52;

    @Schema
    @Valid
    private List<IchimokuLineDto> lines;

    public void setData() {
        this.setDate();
        super.setAfterDay();
    }

    public void setDate() {
        super.date = LocalDate.now().minusDays(30);
    }
}
