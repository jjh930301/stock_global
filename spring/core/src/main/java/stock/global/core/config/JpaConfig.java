package stock.global.core.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import jakarta.persistence.EntityManagerFactory;
import stock.global.core.constants.Constant;

@Configuration
@EnableJpaRepositories(
    entityManagerFactoryRef = Constant.ENTITY_MANAGER_FACTORY, 
    transactionManagerRef = Constant.JPA_TX_MANAGER,
    basePackages={"stock.global.api.repositories"}
)
public class JpaConfig {
  
    @Primary
    @Bean(name = Constant.APP_DATASOURCE)
    public HikariDataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(Constant.DB_URI);
        config.setUsername(Constant.DB_USER);
        config.setPassword(Constant.DB_PASSWORD);
        config.setMaximumPoolSize(5);
        config.setMinimumIdle(2);
        config.setDriverClassName(Constant.DRIVE_CLASS);
        return new HikariDataSource(config);
    }

    @Bean(name = Constant.ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
      JpaVendorAdapter jpaVendorAdapter
    ) {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setPackagesToScan("stock.global.core.entities");
        Map<String,Object> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.show_sql", true);
        jpaProperties.put("hibernate.format_sql", true);
        jpaProperties.put("hibernate.generate-ddl", true);
        if(Constant.DRIVE_CLASS.equals("org.h2.Driver")) {
            jpaProperties.put("hibernate.hbm2ddl.auto", "create-drop");
            jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect"); 
            em.setJpaPropertyMap(jpaProperties);
        }
		em.setJpaVendorAdapter(jpaVendorAdapter);
		return em;
    }

    @Bean(name = Constant.JPA_TX_MANAGER)
    public PlatformTransactionManager transactionManager(
      	@Qualifier(Constant.ENTITY_MANAGER_FACTORY) EntityManagerFactory entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
