package com.kabunov.showcase.data.repository

import com.kabunov.showcase.data.mapper.IrregularVerbDbToDomainMapper
import com.kabunov.showcase.db.DbDataSource
import com.kabunov.showcase.model.IrregularVerb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ContentRepositoryDb @Inject constructor(
    private val dbDataSource: DbDataSource,
    val irregularVerbDbToDomainMapper: IrregularVerbDbToDomainMapper,
) : ContentRepository {

    override fun getIrregularVerbs(): Flow<List<IrregularVerb>> {
        return dbDataSource.getIrregularVerbs().map { dbs -> dbs.map { db -> irregularVerbDbToDomainMapper(db) } }
    }

    override fun getIrregularVerbDetails(id: String): Flow<IrregularVerb> {
        TODO("Not yet implemented")
    }
}