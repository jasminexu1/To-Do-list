interface DAOFacade {
    suspend fun allTisks(): ArrayList<Tisk>
    suspend fun getTisksById(uuid: String): Tisk?
    suspend fun getTisksByTag(tag: String): ArrayList<Tisk>?
    suspend fun getTisksByDueDate(duedate: String): ArrayList<Tisk>?
    suspend fun getTisksByPriority(priority: String): ArrayList<Tisk>?
    suspend fun addTisk(tisk: Tisk): Tisk?
    suspend fun editTisk(tisk: Tisk): Boolean
    suspend fun deleteTisk(uuid: String): Boolean
    suspend fun deleteAll() : Boolean
}