package com.kabunov.showcase.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.kabunov.showcase.db.entity.IrregularVerbDb
import kotlinx.coroutines.flow.Flow

@Dao
interface IrregularVerbDao {

    @Query("SELECT * FROM irregular_verbs")
    fun getAll(): Flow<List<IrregularVerbDb>>

    @Upsert
    suspend fun insertOrUpdate(irregularVerbDb: IrregularVerbDb)
}
