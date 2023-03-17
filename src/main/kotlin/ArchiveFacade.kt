interface ArchiveFacade {
    suspend fun allTisks(): ArrayList<Tisk>
    suspend fun addTisk(tisk: Tisk): Tisk?
    suspend fun editTisk(tisk: Tisk): Boolean
    suspend fun deleteTisk(uuid: String): Boolean

    suspend fun deleteAll() : Boolean
}