package com.kabunov.showcase.data.repository

import com.kabunov.showcase.model.IrregularVerb
import com.kabunov.showcase.model.IrregularVerbDetails
import kotlinx.coroutines.flow.Flow

interface ContentRepository {

    fun getIrregularVerbs(): Flow<List<IrregularVerb>>

    fun getIrregularVerbDetails(id: String): Flow<IrregularVerbDetails?>
}