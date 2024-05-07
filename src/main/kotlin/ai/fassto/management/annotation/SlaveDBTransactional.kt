package ai.fassto.management.annotation

import org.springframework.transaction.annotation.Transactional
import java.lang.annotation.Inherited

@Target(AnnotationTarget.TYPE,AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Transactional("slaveTransactionManager")
@Inherited
annotation class SlaveDBTransactional()