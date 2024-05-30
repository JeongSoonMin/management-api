package com.jesomi.management.global.configuration

import com.zaxxer.hikari.HikariDataSource
import jakarta.persistence.EntityManagerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.support.TransactionSynchronizationManager
import javax.sql.DataSource


@Configuration
@EnableTransactionManagement
@EnableJpaAuditing
@EnableJpaRepositories(
    entityManagerFactoryRef = "entityManagerFactory",
    transactionManagerRef = "transactionManager",
    basePackages = ["com.jesomi.management.persistence.rdb.repository"]
)
class DatabaseConfig {

    @Primary
    @Bean(name = ["masterDataSource"])
    @ConfigurationProperties("application.database.mariadb.master")
    fun masterDataSourceProperties(): HikariDataSource {
        return DataSourceBuilder.create().type(HikariDataSource::class.java).build()
    }


    @Bean(name = ["slaveDataSource"])
    @ConfigurationProperties("application.database.mariadb.slave")
    fun slaveDataSourceProperties(): HikariDataSource {
        return DataSourceBuilder.create().type(HikariDataSource::class.java).build().apply { isReadOnly = true }
    }

    @Bean(name = ["routingDataSource"])
    fun routingDataSource(
        @Qualifier("masterDataSource") masterDataSource: DataSource,
        @Qualifier("slaveDataSource") slaveDataSource: DataSource
    ): DataSource {
        val routingDataSource = RoutingDataSource()
        val dataSources = hashMapOf<Any, Any>()
        dataSources["master"] = masterDataSource
        dataSources["slave"] = slaveDataSource
        routingDataSource.setTargetDataSources(dataSources)
        routingDataSource.setDefaultTargetDataSource(masterDataSource)
        return routingDataSource
    }

    @Bean(name = ["dataSource"])
    fun dataSources(@Qualifier("routingDataSource") routingDataSource: DataSource) =
        LazyConnectionDataSourceProxy(routingDataSource)

    class RoutingDataSource : AbstractRoutingDataSource() {
        override fun determineCurrentLookupKey(): Any =
            when {
                TransactionSynchronizationManager.isCurrentTransactionReadOnly() -> "slave"
                else -> "master"
            }
    }

    @Bean(name = ["entityManagerFactory"])
    fun entityManagerFactory(
        builder: EntityManagerFactoryBuilder,
        @Qualifier("dataSource") routingDataSource: DataSource
    ): LocalContainerEntityManagerFactoryBean {
        return builder
            .dataSource(routingDataSource)
            .packages("com.jesomi.management.persistence.rdb.entity")
            .persistenceUnit("entityManager")
            .build()
    }

    @Bean(name = ["transactionManager"])
    fun transactionManager(
        @Qualifier("entityManagerFactory") masterEntityManagerFactory: EntityManagerFactory
    ): PlatformTransactionManager {
        return JpaTransactionManager(masterEntityManagerFactory)
    }

}