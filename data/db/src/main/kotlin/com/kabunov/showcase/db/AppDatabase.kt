package com.kabunov.showcase.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kabunov.showcase.db.dao.IrregularVerbDao
import com.kabunov.showcase.db.entity.IrregularVerbDb

@Database(
    entities = [
        IrregularVerbDb::class,
    ],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun irregularVerbDao(): IrregularVerbDao
}