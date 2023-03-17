
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object ArchiveFactory {
    fun init() {
        val driverClassName = "org.h2.Driver"
        val jdbcURL = "jdbc:h2:file:~/data/test"
//        val jdbcURL = "jdbc:h2:file:~/.demodb"
        val archiveDatabase = Database.connect(jdbcURL, driverClassName)
        transaction(archiveDatabase) {
//            addLogger(StdOutSqlLogger)
            SchemaUtils.create(archiveTisks)
        }
    }

    suspend fun <T> archiveDbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}