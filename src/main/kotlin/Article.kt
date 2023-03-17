import org.jetbrains.exposed.sql.Table

data class Article(val id: Int, val title: String, val body: String, val tag: String)

object Articles : Table() {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 128)
    val body = varchar("body", 1024)
    val tag = varchar("tag", 1024)

    override val primaryKey = PrimaryKey(id)
}
