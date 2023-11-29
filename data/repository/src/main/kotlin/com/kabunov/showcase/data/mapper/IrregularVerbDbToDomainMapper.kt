package com.kabunov.showcase.data.mapper

import com.kabunov.showcase.db.entity.IrregularVerbDb
import com.kabunov.showcase.model.IrregularVerb
import com.kabunov.showcase.model.IrregularVerbForm
import javax.inject.Inject

class IrregularVerbDbToDomainMapper @Inject constructor() : (IrregularVerbDb) -> IrregularVerb {

    override fun invoke(db: IrregularVerbDb): IrregularVerb {
        return IrregularVerb(
            id = db.verbId.toString(),
            presentSimple = db.v1,
            pastSimple = IrregularVerbForm(db.v2_1, db.v2_2),
            pastParticiple = IrregularVerbForm(db.v3_1, db.v3_2),
            bookmarked =  db.selected ?: false,
        )
    }
}