package stock.global.core.constants;

public class Constant {
    public static final String DB_URI = "127.0.0.1"; // System.getenv("DB_URI");
    public static final String DB_NAME = "global"; // System.getenv("DB_NAME");
    public static final String DB_USER = "root"; // System.getenv("DB_USER");
    public static final String DB_PASSWORD = "jjh930301"; // System.getenv("DB_PASSWORD");
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String MYBATIS_SESSION_FACTORY = "MybatisSqlSessionFactory";
    public static final String ENTITY_MANAGER_FACTORY = "EntityManagerFactory";
    public static final String JPA_TX_MANAGER = "JpaTransactionManager";
    public static final String APP_DATASOURCE = "appDataSource";
}
