package ai.fassto.management.global.configuration

import com.zaxxer.hikari.HikariDataSource
import jakarta.persistence.EntityManagerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
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
@EnableJpaRepositories(
    entityManagerFactoryRef = "entityManagerFactory",
    transactionManagerRef = "transactionManager",
    basePackages = ["ai.fassto.management.repository"]
)
class DatabaseConfig {

    @Primary
    @Bean(name = ["masterDataSourceProperties"])
    @ConfigurationProperties("application.mariadb.master")
    fun masterDataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }


    @Primary
    @Bean(name = ["masterDataSource"])
    @ConfigurationProperties("application.mariadb.master.configuration")
    fun masterDataSource(@Qualifier("masterDataSourceProperties") masterDataSourceProperties: DataSourceProperties): DataSource {
        return masterDataSourceProperties.initializeDataSourceBuilder()
            .type(HikariDataSource::class.java).build()
    }

    @Bean(name = ["slaveDataSourceProperties"])
    @ConfigurationProperties("application.mariadb.slave")
    fun slaveDataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }


    @Bean(name = ["slaveDataSource"])
    @ConfigurationProperties("application.mariadb.slave.configuration")
    fun slaveDataSource(@Qualifier("slaveDataSourceProperties") slaveDataSourceProperties: DataSourceProperties): DataSource {
        return slaveDataSourceProperties.initializeDataSourceBuilder()
            .type(HikariDataSource::class.java).build().apply { isReadOnly = true }
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
            .packages("ai.fassto.management.entity")
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