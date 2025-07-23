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
import lombok.extern.slf4j.Slf4j;
import stock.global.core.constants.DB;

@Configuration
@EnableJpaRepositories(
    entityManagerFactoryRef = DB.ENTITY_MANAGER_FACTORY, 
    transactionManagerRef = DB.JPA_TX_MANAGER,
    basePackages={
        "stock.global.api.repositories",
        "stock.global.kiwoom.repository"
    }
)
@Slf4j
public class JpaConfig {
  
    @Primary
    @Bean(name = DB.APP_DATASOURCE)
    public HikariDataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DB.DB_URI);
        config.setUsername(DB.DB_USER);
        config.setPassword(DB.DB_PASSWORD);
        config.setMaximumPoolSize(5);
        config.setMinimumIdle(2);
        config.setDriverClassName(DB.DRIVE_CLASS);
        return new HikariDataSource(config);
    }

    @Bean(name = DB.ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
      JpaVendorAdapter jpaVendorAdapter
    ) {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setPackagesToScan("stock.global.core.entities");
        Map<String,Object> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.format_sql", true);
        jpaProperties.put("hibernate.show_sql", true);
        if(DB.DRIVE_CLASS.equals("org.h2.Driver")) {
            jpaProperties.put("hibernate.generate-ddl", true);
            jpaProperties.put("hibernate.hbm2ddl.auto", "create-drop");
            jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect"); 
        } else {
            jpaProperties.put("hibernate.hbm2ddl.auto", "update");
        }
        log.info("jpaProperties {}" , jpaProperties);
        em.setJpaPropertyMap(jpaProperties);
		em.setJpaVendorAdapter(jpaVendorAdapter);
		return em;
    }

    @Bean(name = DB.JPA_TX_MANAGER)
    public PlatformTransactionManager transactionManager(
      	@Qualifier(DB.ENTITY_MANAGER_FACTORY) EntityManagerFactory entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
