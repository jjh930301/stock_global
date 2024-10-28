package stock.global.core.config;

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
    transactionManagerRef = Constant.JPA_TX_MANAGER
)
public class JpaConfig {
  
	@Primary
    @Bean(name = Constant.APP_DATASOURCE)
    public HikariDataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://"+Constant.DB_URI+":3310/"+Constant.DB_NAME+"?characterEncoding=UTF-8");
        config.setUsername(Constant.DB_USER);
        config.setPassword(Constant.DB_PASSWORD);
        config.setMaximumPoolSize(5);
        config.setMinimumIdle(2);
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
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
		
		em.setPackagesToScan("stock.global.core.global.entities");
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
