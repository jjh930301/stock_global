package stock.global.api.domain.kr.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import stock.global.api.domain.kr.dto.IndexDayCandleDto;
import stock.global.api.repositories.KrIndexDayCandleRepository;
import stock.global.core.models.ApiRes;

@Service
public class KrDayCandleService {

    private final KrIndexDayCandleRepository indexDayCandleRepository;

    public KrDayCandleService(
        KrIndexDayCandleRepository indexDayCandleRepository
    ) {
        this.indexDayCandleRepository = indexDayCandleRepository;
    }

    public ApiRes<List<IndexDayCandleDto>> getIndexDayCandle(
        int market,
        LocalDate date
    ) {
        return ApiRes
            .<List<IndexDayCandleDto>>builder()
            .payload(this.indexDayCandleRepository.findAllByMarket(market , date))
            .status(200)
            .build();
    }
}
