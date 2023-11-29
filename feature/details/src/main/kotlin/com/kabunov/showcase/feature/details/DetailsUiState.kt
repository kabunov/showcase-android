package com.kabunov.showcase.feature.details

sealed interface DetailsUiState {
    data object Loading : DetailsUiState

    data object LoadFailed : DetailsUiState //TODO

    data class Success(
        val irregularVerb: IrregularVerbDetailsViewData,
    ) : DetailsUiState
}

data class IrregularVerbDetailsViewData(
    val presentSimple: String,
    val pastSimple: String,
    val pastParticiple: String,
    val definition: String,
)