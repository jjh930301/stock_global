package stock.global.api.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import stock.global.api.domain.daycandle.dto.AvgLineDto;

@Mapper
public interface DayCandleDao {
    List<HashMap<String , Object>> findTickers(AvgLineDto dto);
}
