package stock.global.api.domain.daycandle.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import stock.global.api.dao.DayCandleDao;
import stock.global.api.domain.daycandle.dto.AvgLineDto;
import stock.global.api.domain.daycandle.dto.IchimokuDto;
import stock.global.core.models.ApiRes;

@Slf4j
@Service
public class DayCandleService {
    public final DayCandleDao dayCandleDao;

    public DayCandleService(
        DayCandleDao dayCandleDao
    ) {
        this.dayCandleDao = dayCandleDao;
    }
    /**
     * 이후 캔들에서 가장 높은 수익을 계산
     */
    private void setHighPercent(List<HashMap<String , Object>> rows) {
        for (HashMap<String , Object> row : rows) {
            BigDecimal close =  (BigDecimal)row.get("close");
            List<BigDecimal> afters = new ArrayList<>();
            row.keySet().forEach((key) -> {
                if(key.contains("after")) {
                    afters.add((BigDecimal)row.get(key));
                }
            });
            if(!afters.isEmpty()) {
                BigDecimal high = afters.stream().max(BigDecimal::compareTo).get();
                row.put(
                    "per", 
                    close
                        .divide(high, 4, RoundingMode.UP)
                        .multiply(new BigDecimal(100))
                        .subtract(new BigDecimal(100))
                );
            }
        }
    }

    public ApiRes<List<HashMap<String,Object>>> getAvgLine(
        AvgLineDto dto
    ) {
        dto.setValues();
        List<HashMap<String , Object>> rows = this.dayCandleDao.findAvgTickers(dto);
        this.setHighPercent(rows);
        return ApiRes
            .<List<HashMap<String,Object>>>builder()
            .payload(rows)
            .build();
    }

    public ApiRes<?> getIchimoku(IchimokuDto dto) {
        dto.setData();
        List<HashMap<String , Object>> rows = this.dayCandleDao.findIchimokuTickers(dto);
        this.setHighPercent(rows);
        return ApiRes
            .builder()
            .payload(rows)
            .build();
    }
}
