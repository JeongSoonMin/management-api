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
    entityManagerFactoryRef = "masterEntityManagerFactory",
    transactionManagerRef = "masterTransactionManager",
    basePackages = ["ai.fassto.management.persistence.master"]
)
class MasterDatabaseConfig {
    @Primary
    @Bean(name = ["masterDataSourceProperties"])
    @ConfigurationProperties("application.mariadb.master")
    fun dataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }


    @Primary
    @Bean(name = ["masterDataSource"])
    @ConfigurationProperties("application.mariadb.master.configuration")
    fun dataSource(@Qualifier("masterDataSourceProperties") masterDataSourceProperties: DataSourceProperties): DataSource {
        return masterDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource::class.java).build()
    }

    @Primary
    @Bean(name = ["masterEntityManagerFactory"])
    fun entityManagerFactory(
        builder: EntityManagerFactoryBuilder,
        @Qualifier("masterDataSource") deviceDataSource: DataSource
    ): LocalContainerEntityManagerFactoryBean {
        return builder
            .dataSource(deviceDataSource)
            .packages("ai.fassto.management.entity")
            .persistenceUnit("master")
            .build()
    }

    @Primary
    @Bean(name = ["masterTransactionManager"])
    fun transactionManager(
        @Qualifier("masterEntityManagerFactory") masterEntityManagerFactory: EntityManagerFactory
    ): PlatformTransactionManager {
        return JpaTransactionManager(masterEntityManagerFactory)
    }

}