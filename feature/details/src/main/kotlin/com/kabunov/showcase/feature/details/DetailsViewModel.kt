package com.kabunov.showcase.feature.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabunov.showcase.domain.usecase.GetDetailsUseCase
import com.kabunov.showcase.domain.usecase.ToggleBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.lang.IllegalStateException
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    getDetailsUseCase: GetDetailsUseCase,
    val toggleBookmarkUseCase: ToggleBookmarkUseCase,
    irregularVerbDetailsDomainToViewDataMapper: IrregularVerbDetailsDomainToViewDataMapper,
    savedStateHandle: SavedStateHandle // https://github.com/google/dagger/issues/2287
) : ViewModel() {

    private val itemId =
        savedStateHandle.getLiveData<String>(ID_KEY).value ?: throw IllegalStateException("Incorrect id")

    val detailsUiState: StateFlow<DetailsUiState> =
        getDetailsUseCase.invoke(itemId).map { item ->
            if (item != null) {
                DetailsUiState.Success(irregularVerbDetailsDomainToViewDataMapper(item))
            } else {
                DetailsUiState.LoadFailed
            }
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = DetailsUiState.Loading,
            )

    fun toggleBookmark(id: String, bookmarked: Boolean) {
        viewModelScope.launch {
            toggleBookmarkUseCase(id, bookmarked)
        }
    }

    companion object {

        const val ID_KEY = "id"
    }
}

