package stock.global.core.constants;

public class Constant {
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String JWT_SECRET = System.getenv("JWT_SECRET") == null 
        ? "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" 
        : System.getenv("JWT_SECRET");
    public static final String KR_DAY_CANDLE_STREAM = "krDayCandleStream";
    public static final String KR_INDEX_DAY_CANDLE_STREAM = "krIndexDayCandleStream";
    public static final String KR_CANDLE_GROUP = "krCandleGroup";
    public static final String KR_CANDLE_GROUP_NAME = "krCandleGroupName";
}
