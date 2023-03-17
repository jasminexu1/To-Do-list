
import DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DAOFacadeImpl : DAOFacade{

    private fun resultRowToTisk(row: ResultRow) = Tisk(
        uuid = row[Tisks.uuid],
        tag = row[Tisks.tag],
        priority = row[Tisks.priority],
        description =  row[Tisks.description],
        text =  row[Tisks.text],
        duedate =  row[Tisks.duedate],
    )

    override suspend fun allTisks(): ArrayList<Tisk> = dbQuery {
        ArrayList(Tisks.selectAll().map(::resultRowToTisk))
    }

    override suspend fun getTisksById(uuid: String): Tisk? = dbQuery {
        Tisks
            .select { Tisks.uuid eq uuid }
            .map(::resultRowToTisk)
            .singleOrNull()
    }

    override suspend fun getTisksByTag(tag: String): ArrayList<Tisk> = dbQuery {
        ArrayList(Tisks
            .select { Tisks.tag eq tag }
            .map(::resultRowToTisk))
    }

    override suspend fun getTisksByDueDate(duedate: String): ArrayList<Tisk> = dbQuery{
        ArrayList(Tisks
            .select { Tisks.duedate eq duedate }
            .map(::resultRowToTisk))
    }

    override suspend fun getTisksByPriority(priority: String): ArrayList<Tisk> = dbQuery{
        ArrayList(Tisks
            .select { Tisks.priority eq priority }
            .map(::resultRowToTisk))
    }


    override suspend fun addTisk(tisk: Tisk): Tisk? = dbQuery {
        val insertStatement = Tisks.insert {
            it[uuid] = tisk.uuid
            it[tag] = tisk.tag
            it[priority] = tisk.priority
            it[description] = tisk.description
            it[text] = tisk.text
            it[duedate] = tisk.duedate
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToTisk)
    }

    override suspend fun editTisk(tisk: Tisk): Boolean = dbQuery {
        Tisks.update({ Tisks.uuid eq tisk.uuid }) {
            it[uuid] = tisk.uuid
            it[tag] = tisk.tag
            it[priority] = tisk.priority
            it[description] = tisk.description
            it[text] = tisk.text
            it[duedate] = tisk.duedate
        } > 0
    }

    override suspend fun deleteTisk(uuid: String): Boolean = dbQuery {
        Tisks.deleteWhere { Tisks.uuid eq uuid } > 0
    }

    override suspend fun deleteAll(): Boolean = dbQuery {
        Tisks.deleteAll() > 0
    }

//    val dao: DAOFacade = DAOFacadeImpl().apply {
//        runBlocking {
//            if(allArticles().isEmpty()) {
//                addNewArticle("The drive to develop!", "...it's what keeps me going.")
//            }
//        }
//    }
}