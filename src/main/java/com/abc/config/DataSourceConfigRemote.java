package com.abc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
        entityManagerFactoryRef = "entityManagerFactoryRemote",
        transactionManagerRef = "transactionManagerRemote",
        basePackages = {"com.abc.remote"})
public class DataSourceConfigRemote {
    @Resource
    private Properties jpaProperties;

    @Autowired
    @Qualifier("remoteDb")
    private DataSource remoteDataSource;

    @Primary
    @Bean(name = "entityManagerRemote")
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return Objects.requireNonNull(entityManagerFactoryRemote(builder).getObject()).createEntityManager();
    }

    @Primary
    @Bean(name = "entityManagerFactoryRemote")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryRemote(EntityManagerFactoryBuilder builder) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = builder
                .dataSource(remoteDataSource)
                .packages("com.abc.remote") //设置实体类所在位置
                .persistenceUnit("remotePersistenceUnit")
                .build();
        entityManagerFactory.setJpaProperties(jpaProperties);
        return entityManagerFactory;
    }

    @Primary
    @Bean(name = "transactionManagerRemote")
    public PlatformTransactionManager transactionManagerRemote(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactoryRemote(builder).getObject()));
    }
}
