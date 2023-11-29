package com.kabunov.showcase.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "irregular_verbs")
class IrregularVerbDb(
   @PrimaryKey(autoGenerate = true)
   @ColumnInfo(name = "verb_id")
   val verbId: Int? = 0,

   @ColumnInfo(name = "v1")
   val v1: String?,

   @ColumnInfo(name = "v2_1")
   val v2_1: String?,

   @ColumnInfo(name = "v2_2")
   val v2_2: String?,

   @ColumnInfo(name = "v3_1")
   val v3_1: String?,

   @ColumnInfo(name = "v3_2")
   val v3_2: String?,

   @ColumnInfo(name = "level")
   val level: Int?,

   @ColumnInfo(name = "definition")
   val definition: String?,

   @ColumnInfo(name = "example")
   val example: String?
)