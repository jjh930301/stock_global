package stock.global.api.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import stock.global.api.domain.daycandle.dto.AvgLineDto;
import stock.global.api.domain.daycandle.dto.IchimokuDto;

@Mapper
public interface DayCandleDao{
    List<HashMap<String , Object>> findAvgTickers(AvgLineDto dto);
    List<HashMap<String , Object>> findIchimokuTickers(IchimokuDto dto);
}
