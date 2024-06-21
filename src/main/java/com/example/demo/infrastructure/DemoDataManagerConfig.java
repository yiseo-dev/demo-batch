package com.example.demo.infrastructure;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = {
        "com.example.demo.domain.repository"
    },
    entityManagerFactoryRef = "demoEntityManagerFactory",
    transactionManagerRef = "demoTransactionManager"
)
public class DemoDataManagerConfig {

  private final DataSource demoDataSource;

  public DemoDataManagerConfig(
      @Qualifier("demoDataSource") DataSource demoDataSource) {
    this.demoDataSource = demoDataSource;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean demoEntityManagerFactory() {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    vendorAdapter.setGenerateDdl(true);

    em.setDataSource(this.demoDataSource);
    em.setPersistenceUnitName("demoEntityManager");
    em.setPackagesToScan(
        "com.example.demo.domain.entity"
    );
    em.setJpaVendorAdapter(vendorAdapter);
    em.setJpaProperties(demoJpaProperties());
    em.afterPropertiesSet();
    return em;
  }

  @Bean
  public JdbcTemplate demoJdbcTemplate(@Qualifier("demoDataSource") DataSource demoDataSource) {
    return new JdbcTemplate(demoDataSource);
  }

  private Properties demoJpaProperties() {
    Properties properties = new Properties();
    properties.setProperty(AvailableSettings.HBM2DDL_AUTO, "update");
    properties.setProperty(AvailableSettings.SHOW_SQL, "true");
    properties.setProperty(AvailableSettings.ALLOW_UPDATE_OUTSIDE_TRANSACTION, "true");
    return properties;
  }
}
