package ai.fassto.management.global.configuration

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class QueryDslConfig{

    @PersistenceContext(unitName = "masterEntityManager")
    private val masterEntityManager: EntityManager? = null

    @PersistenceContext(unitName = "slaveEntityManager")
    private val slaveEntityManager: EntityManager? = null

    @Bean
    fun masterJpaQueryFactory(): JPAQueryFactory {
        return JPAQueryFactory(masterEntityManager)
    }

    @Bean
    fun slaveJpaQueryFactory(): JPAQueryFactory {
        return JPAQueryFactory(slaveEntityManager)
    }
}