package com.kabunov.showcase.feature.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabunov.showcase.domain.usecase.GetListUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    getListUsecase: GetListUsecase,
    irregularVerbDomainToViewDataMapper: IrregularVerbDomainToViewDataMapper
) : ViewModel() {

    val listUiState: StateFlow<ListUiState> =
        getListUsecase.invoke().map { items ->
            ListUiState.Success(items.map { item -> irregularVerbDomainToViewDataMapper(item) })
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ListUiState.Loading,
            )
}

