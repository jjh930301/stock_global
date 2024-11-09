package stock.global.api.domain.daycandle.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import stock.global.api.domain.daycandle.dto.AvgLineDto;
import stock.global.api.domain.daycandle.service.DayCandleService;
import stock.global.core.annotations.ControllerInfo;
import stock.global.core.annotations.SwaggerInfo;
import stock.global.core.models.ApiRes;

@Tag(name="daycandle")
@ControllerInfo(path = "daycandle")
public class DayCandleController {
    public final DayCandleService dayCandleService;

    public DayCandleController(DayCandleService dayCandleService) {
        this.dayCandleService = dayCandleService;
    }

    @SwaggerInfo(summary="이동 평균선 백테스트")
    @GetMapping("/avgline")
    public ResponseEntity<ApiRes<?>> getAvgLine(
        @Valid AvgLineDto dto
    ) {
        return ResponseEntity
            .ok()
            .body(this.dayCandleService.getAvgLine(dto));
    }
}
