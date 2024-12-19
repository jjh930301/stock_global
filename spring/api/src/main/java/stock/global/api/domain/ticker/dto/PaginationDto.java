package stock.global.api.domain.ticker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationDto {
    @Schema(name="page")
    private int page;

    @Schema(name="size")
    private int size;
}
