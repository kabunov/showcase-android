package com.kabunov.showcase.data.mapper

import com.kabunov.showcase.db.entity.IrregularVerbDb
import com.kabunov.showcase.model.IrregularVerbDetails
import com.kabunov.showcase.model.IrregularVerbForm
import javax.inject.Inject

class IrregularVerbDetailsDbToDomainMapper @Inject constructor() : (IrregularVerbDb) -> IrregularVerbDetails {

    override fun invoke(db: IrregularVerbDb): IrregularVerbDetails {
        return IrregularVerbDetails(
            id = db.verbId.toString(),
            presentSimple = db.v1 ?: "",
            pastSimple = IrregularVerbForm(db.v2_1, db.v2_2),
            pastParticiple = IrregularVerbForm(db.v3_1, db.v3_2),
            description = db.definition ?: ""
        )
    }
}