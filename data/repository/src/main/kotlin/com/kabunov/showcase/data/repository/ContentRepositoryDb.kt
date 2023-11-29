package com.kabunov.showcase.data.repository

import com.kabunov.showcase.data.mapper.IrregularVerbDbToDomainMapper
import com.kabunov.showcase.data.mapper.IrregularVerbDetailsDbToDomainMapper
import com.kabunov.showcase.db.DbDataSource
import com.kabunov.showcase.model.IrregularVerb
import com.kabunov.showcase.model.IrregularVerbDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ContentRepositoryDb @Inject constructor(
    private val dbDataSource: DbDataSource,
    val irregularVerbDbToDomainMapper: IrregularVerbDbToDomainMapper,
    val irregularVerbDetailsDbToDomainMapper: IrregularVerbDetailsDbToDomainMapper
) : ContentRepository {

    override fun getIrregularVerbs(): Flow<List<IrregularVerb>> {
        return dbDataSource.getIrregularVerbs().map { dbs -> dbs.map { db -> irregularVerbDbToDomainMapper(db) } }
    }

    override fun getIrregularVerbDetails(id: String): Flow<IrregularVerbDetails?> {
        val idInt = id.toIntOrNull()
        if (idInt == null) {
            throw IllegalArgumentException("Incorrect param $id")
        } else {
            return dbDataSource.getIrregularVerbDetails(idInt).map { item ->
                item?.let { irregularVerbDetailsDbToDomainMapper(it) }
            }
        }
    }

    override suspend fun toggleBookmark(id: String, bookmarked: Boolean): Int {
        val idInt = id.toIntOrNull()
        if (idInt == null) {
            throw IllegalArgumentException("Incorrect param $id")
        } else {
            return dbDataSource.toggleBookmark(idInt, bookmarked)
        }
    }
}