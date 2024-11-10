package stock.global.api.domain.daycandle.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import stock.global.api.domain.daycandle.dto.AvgLineDto;
import stock.global.api.domain.daycandle.dto.IchimokuDto;
import stock.global.api.domain.daycandle.service.DayCandleService;
import stock.global.core.annotations.ControllerInfo;
import stock.global.core.annotations.SwaggerInfo;
import stock.global.core.models.ApiRes;

@Slf4j
@Tag(name="daycandle")
@ControllerInfo(path = "daycandle")
public class DayCandleController {
    public final DayCandleService dayCandleService;

    public DayCandleController(DayCandleService dayCandleService) {
        this.dayCandleService = dayCandleService;
    }

    @SwaggerInfo(
        summary="이동 평균선 백테스트" , 
        description="""
                백테스팅으로 통계를 확인하기 위한 endpoint
                """
    )
    @GetMapping("/avgline")
    public ResponseEntity<ApiRes<?>> getAvgLine(
        @ParameterObject @Valid AvgLineDto dto
    ) {
        return ResponseEntity
            .ok()
            .body(this.dayCandleService.getAvgLine(dto));
    }

    @SwaggerInfo(
        summary="일목균형표 테스트",
        description="""
                일목균형표 
                https://ko.wikipedia.org/wiki/%EC%9D%BC%EB%AA%A9%EA%B7%A0%ED%98%95%ED%91%9C
                """
    )
    @PostMapping("/ichimoku")
    public ResponseEntity<ApiRes<?>> getIchimoku(
        @RequestBody @Valid IchimokuDto dto
    ) {
        return ResponseEntity
            .ok()
            .body(this.dayCandleService.getIchimoku(dto));
    }
}
