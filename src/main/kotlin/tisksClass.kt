
import com.google.gson.annotations.SerializedName
import org.jetbrains.exposed.sql.Table

data class Tisk (

    @SerializedName("uuid"    ) var uuid    : String,
    @SerializedName("tag"    ) var tag    : String = "untagged",
    @SerializedName("priority"    ) var priority    : String = "1",
    @SerializedName("description"    ) var description    : String = "",
    @SerializedName("text"    ) var text    : String,
    @SerializedName("duedate" ) var duedate : String

)

object Tisks : Table() {
    val uuid = varchar("uuid", 128)
    val tag  = varchar("tag", 128)
    val priority = varchar("priority", 128)
    val description = varchar("description", 1024)
    val text = varchar("text", 155)
    val duedate = varchar("duedate", 128)

    override val primaryKey = PrimaryKey(uuid)
}

object archiveTisks : Table() {
    val uuid = varchar("uuid", 128)
    val tag  = varchar("tag", 128)
    val priority = varchar("priority", 128)
    val description = varchar("description", 1024)
    val text = varchar("text", 155)
    val duedate = varchar("duedate", 128)

    override val primaryKey = PrimaryKey(uuid)
}