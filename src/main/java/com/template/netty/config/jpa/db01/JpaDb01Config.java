package com.template.netty.config.jpa.db01;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = {"com.template.netty.db.db01.repo"},
        entityManagerFactoryRef = "db01EntityManager",
        transactionManagerRef = "db01TransactionManager"
)
public class JpaDb01Config {

    @Bean(name = "db01TransactionManager")
    public PlatformTransactionManager db01TransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(db01EntityManager().getObject());
        return transactionManager;
    }

    @Bean(name = "db01EntityManager")
    public LocalContainerEntityManagerFactoryBean db01EntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(db01DataSource());
        em.setPackagesToScan("com.template.netty.db.db01.entity");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return em;
    }

    @Bean(name = "db01DataSource")
    @ConfigurationProperties(prefix = "spring.datasources.sql.db01")
    public DataSource db01DataSource() {
        return DataSourceBuilder.create().build();
    }
}