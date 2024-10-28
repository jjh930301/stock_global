package stock.global.core.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

import stock.global.core.constants.Constant;

@Configuration
@EnableTransactionManagement
@MapperScan(
    basePackages = "stock.global",
    sqlSessionFactoryRef = Constant.MYBATIS_SESSION_FACTORY
)
public class MybatisConfig {

    @Bean(name = Constant.MYBATIS_SESSION_FACTORY)
    public SqlSessionFactory sqlSessionFactory(
        @Qualifier(Constant.APP_DATASOURCE) HikariDataSource dataSource
    ) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(
            new PathMatchingResourcePatternResolver()
            .getResources("classpath*:mappers/**/*.xml")
        );
        return factoryBean.getObject();
    }

    @Bean(name = "MybatisSqlTemplate")
    public SqlSessionTemplate sqlSessionTemplate(
        @Qualifier(Constant.MYBATIS_SESSION_FACTORY) SqlSessionFactory sqlSessionFactory
    ) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "MybatisTransactionManager")
    public PlatformTransactionManager txManager(
        @Qualifier(Constant.APP_DATASOURCE) HikariDataSource datasource
    ) {
        return new DataSourceTransactionManager(datasource);
    }
}
