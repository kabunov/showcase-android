package com.kabunov.showcase.db

import com.kabunov.showcase.db.entity.IrregularVerbDb
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DbDataSource @Inject constructor(private val appDatabase: AppDatabase) {

    fun getIrregularVerbs(): Flow<List<IrregularVerbDb>> = appDatabase.irregularVerbDao().getAll()

    fun getIrregularVerbDetails(id: Int): Flow<IrregularVerbDb?> = appDatabase.irregularVerbDao().get(id)

    suspend fun toggleBookmark(id: Int, bookmarked: Boolean): Int = appDatabase.irregularVerbDao().toggleBookmark(id, bookmarked)
}