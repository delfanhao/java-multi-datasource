package com.abc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryLocal",
        transactionManagerRef = "transactionManagerLocal",
        basePackages = {"com.abc.local"})
public class DataSourceConfigLocal {
    @Autowired
    @Qualifier("localDb")
    private DataSource localDataSource;

    @Resource
    private Properties jpaProperties;

    @Bean(name = "entityManagerLocal")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return Objects.requireNonNull(entityManagerFactoryLocal(builder).getObject()).createEntityManager();
    }

    @Bean(name = "entityManagerFactoryLocal")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryLocal(EntityManagerFactoryBuilder builder) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = builder
                .dataSource(localDataSource)
                .packages("com.abc.local")
                .persistenceUnit("LocalPersistenceUnit")
                .build();
        entityManagerFactory.setJpaProperties(jpaProperties);
        return entityManagerFactory;
    }

    @Bean(name = "transactionManagerLocal")
    public PlatformTransactionManager transactionManagerLocal(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactoryLocal(builder).getObject()));
    }
}
