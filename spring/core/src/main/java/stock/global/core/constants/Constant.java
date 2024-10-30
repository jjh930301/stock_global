package stock.global.core.constants;

public class Constant {
    public static final String DB_URI = 
        System.getenv("MYSQL_HOST") == null 
            ? "127.0.0.1" 
            : System.getenv("MYSQL_HOST");
    public static final String DB_NAME = 
        System.getenv("MYSQL_DATABASE") == null 
            ? "global" 
            : System.getenv("MYSQL_DATABASE");
    public static final String DB_PASSWORD = 
        System.getenv("MYSQL_ROOT_PASSWORD") == null 
            ? "jjh930301" 
            : System.getenv("MYSQL_ROOT_PASSWORD");
    public static final String DB_USER = 
        System.getenv("MYSQL_USER") == null 
            ? "root" 
            : System.getenv("DB_USER");
    public static final String DB_PORT = 
        System.getenv("MYSQL_PORT") == null 
            ? "3310" 
            : System.getenv("MYSQL_PORT");
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String MYBATIS_SESSION_FACTORY = "MybatisSqlSessionFactory";
    public static final String ENTITY_MANAGER_FACTORY = "EntityManagerFactory";
    public static final String JPA_TX_MANAGER = "JpaTransactionManager";
    public static final String APP_DATASOURCE = "appDataSource";
    public static final String JWT_SECRET = System.getenv("JWT_SERCERT") == null 
        ? "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" 
        : System.getenv("JWT_SERCERT");
}
