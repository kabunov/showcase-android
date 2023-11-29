package com.kabunov.showcase.feature.details

import com.kabunov.showcase.model.IrregularVerbDetails
import com.kabunov.showcase.model.IrregularVerbForm
import javax.inject.Inject

class IrregularVerbDetailsDomainToViewDataMapper @Inject constructor() :
        (IrregularVerbDetails) -> IrregularVerbDetailsViewData {

    override fun invoke(item: IrregularVerbDetails): IrregularVerbDetailsViewData {
        return IrregularVerbDetailsViewData(
            presentSimple = item.presentSimple,
            pastSimple = formatVersionTitles(item.pastSimple),
            pastParticiple = formatVersionTitles(item.pastParticiple),
            definition = item.description,
            bookmarked = item.bookmarked,
        )
    }

    private fun formatVersionTitles(
        verbForm: IrregularVerbForm,
    ): String {
        val sb = StringBuilder("")
        if (verbForm.version1 != null) {
            sb.append(verbForm.version1)
        }
        if (verbForm.version2 != null) {
            sb.append("/").append(verbForm.version2)
        }
        return sb.toString()
    }
}