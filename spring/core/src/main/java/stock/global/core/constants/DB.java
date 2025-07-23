package stock.global.core.constants;

public class DB {
    public static final String DB_PORT = 
        System.getenv("MYSQL_PORT") == null 
            ? "3310" 
            : System.getenv("MYSQL_PORT");
    public static final String DB_NAME = 
        System.getenv("MYSQL_DATABASE") == null 
            ? "testdb" 
            : System.getenv("MYSQL_DATABASE");
    public static final String DB_URI = 
        System.getenv("MYSQL_HOST") == null 
            ? "jdbc:h2:mem:testdb" 
            : "jdbc:mysql://"+System.getenv("MYSQL_HOST")+":3306/"+DB.DB_NAME+"?characterEncoding=UTF-8";
    
    public static final String DB_PASSWORD = 
        System.getenv("MYSQL_ROOT_PASSWORD") == null 
            ? "" 
            : System.getenv("MYSQL_ROOT_PASSWORD");
    public static final String DB_USER = 
        System.getenv("MYSQL_USER") == null 
            ? "sa" 
            : System.getenv("MYSQL_USER");
    
    public static final String DRIVE_CLASS = System.getenv("DRIVER_CLASS") == null 
            ? "org.h2.Driver"
            : System.getenv("DRIVER_CLASS");
    public static final String APP_DATASOURCE = "appDataSource";
    public static final String MYBATIS_SESSION_FACTORY = "MybatisSqlSessionFactory";
    public static final String ENTITY_MANAGER_FACTORY = "EntityManagerFactory";
    public static final String JPA_TX_MANAGER = "JpaTransactionManager";
}
