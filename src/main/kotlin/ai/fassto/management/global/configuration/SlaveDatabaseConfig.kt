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
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "slaveEntityManagerFactory",
    transactionManagerRef = "slaveTransactionManager",
    basePackages = ["ai.fassto.management.persistence.slave"]
)
class SlaveDatabaseConfig {

    @Bean(name = ["slaveDataSourceProperties"])
    @ConfigurationProperties("application.mariadb.slave")
    fun dataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }


    @Bean(name = ["slaveDataSource"])
    @ConfigurationProperties("application.mariadb.slave.configuration")
    fun dataSource(@Qualifier("slaveDataSourceProperties") slaveDataSourceProperties: DataSourceProperties): DataSource {
        return slaveDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource::class.java).build()
    }

    @Bean(name = ["slaveEntityManagerFactory"])
    fun entityManagerFactory(
        builder: EntityManagerFactoryBuilder,
        @Qualifier("slaveDataSource") slaveDataSource: DataSource
    ): LocalContainerEntityManagerFactoryBean {
        return builder
            .dataSource(slaveDataSource)
            .packages("ai.fassto.management.entity")
            .persistenceUnit("slaveEntityManager")
            .build()
    }

    @Bean(name = ["slaveTransactionManager"])
    fun transactionManager(
        @Qualifier("slaveEntityManagerFactory") slaveEntityManagerFactory: EntityManagerFactory
    ): PlatformTransactionManager {
        return JpaTransactionManager(slaveEntityManagerFactory)
    }
}