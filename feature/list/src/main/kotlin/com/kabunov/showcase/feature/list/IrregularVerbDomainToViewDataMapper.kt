package com.kabunov.showcase.feature.list

import com.kabunov.showcase.model.IrregularVerb
import com.kabunov.showcase.model.IrregularVerbForm
import javax.inject.Inject

class IrregularVerbDomainToViewDataMapper @Inject constructor() : (IrregularVerb) -> IrregularVerbViewData {

    override fun invoke(item: IrregularVerb): IrregularVerbViewData {
        return IrregularVerbViewData(
            id = item.id,
            title = item.presentSimple,
            subtitle = formatVersionTitles(item.pastSimple) + "  " + formatVersionTitles(item.pastParticiple),
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