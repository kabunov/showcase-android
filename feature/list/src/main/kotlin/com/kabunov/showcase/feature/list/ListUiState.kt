package com.kabunov.showcase.feature.list

sealed interface ListUiState {
    data object Loading : ListUiState

    data object LoadFailed : ListUiState //TODO

    data class Success(
        val irregularVerbs: List<IrregularVerbViewData>,
    ) : ListUiState
}

data class IrregularVerbViewData(
    val id: String,
    val title: String,
    val subtitle: String,
)