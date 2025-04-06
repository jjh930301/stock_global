package stock.global.api.domain.kr.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.tags.Tag;
import stock.global.api.domain.kr.dto.IndexDayCandleDto;
import stock.global.api.domain.kr.service.KrDayCandleService;
import stock.global.core.annotations.ControllerInfo;
import stock.global.core.annotations.SwaggerInfo;
import stock.global.core.models.ApiRes;


@Tag(name = "kr/daycandle")
@ControllerInfo(path = "kr/daycandle")
public class KrDayCandleController {

    public final KrDayCandleService krDayCandleService;

    public KrDayCandleController(KrDayCandleService krDayCandleService) {
        this.krDayCandleService = krDayCandleService;
    }
    
    @SwaggerInfo(
        summary="지수 차트"
    )
    @GetMapping("/index")
    public ApiRes<List<IndexDayCandleDto>> getIndexDayCandle(
        @RequestParam(name = "market" , required = true) int market,
        @RequestParam(name = "date" , required = false) LocalDate date
    ) {
        return this.krDayCandleService.getIndexDayCandle(market , date);
    }
    
}
