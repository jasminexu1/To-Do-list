
import ArchiveFactory.archiveDbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class ArchiveFacadeImpl : ArchiveFacade{

    private fun resultRowToTisk(row: ResultRow) = Tisk(
        uuid = row[archiveTisks.uuid],
        tag = row[archiveTisks.tag],
        priority = row[archiveTisks.priority],
        description =  row[archiveTisks.description],
        text =  row[archiveTisks.text],
        duedate =  row[archiveTisks.duedate],
    )

    override suspend fun allTisks(): ArrayList<Tisk> = archiveDbQuery {
        ArrayList(archiveTisks.selectAll().map(::resultRowToTisk))
    }

    override suspend fun addTisk(tisk: Tisk): Tisk? = archiveDbQuery {
        val insertStatement = archiveTisks.insert {
            it[uuid] = tisk.uuid
            it[tag] = tisk.tag
            it[priority] = tisk.priority
            it[description] = tisk.description
            it[text] = tisk.text
            it[duedate] = tisk.duedate
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToTisk)
    }

    override suspend fun editTisk(tisk: Tisk): Boolean = archiveDbQuery {
        archiveTisks.update({ archiveTisks.uuid eq tisk.uuid }) {
            it[uuid] = tisk.uuid
            it[tag] = tisk.tag
            it[priority] = tisk.priority
            it[description] = tisk.description
            it[text] = tisk.text
            it[duedate] = tisk.duedate
        } > 0
    }

    override suspend fun deleteTisk(uuid: String): Boolean = archiveDbQuery {
        archiveTisks.deleteWhere { archiveTisks.uuid eq uuid } > 0
    }

    override suspend fun deleteAll(): Boolean = archiveDbQuery {
        archiveTisks.deleteAll() > 0
    }

//    val dao: DAOFacade = DAOFacadeImpl().apply {
//        runBlocking {
//            if(allArticles().isEmpty()) {
//                addNewArticle("The drive to develop!", "...it's what keeps me going.")
//            }
//        }
//    }
}