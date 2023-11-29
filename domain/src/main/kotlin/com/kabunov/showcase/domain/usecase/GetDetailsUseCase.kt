package com.kabunov.showcase.domain.usecase

import com.kabunov.showcase.data.repository.ContentRepository
import com.kabunov.showcase.model.IrregularVerbDetails
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDetailsUseCase @Inject constructor(private val contentRepository: ContentRepository) {

    operator fun invoke(id: String): Flow<IrregularVerbDetails?> {
        return contentRepository.getIrregularVerbDetails(id)
    }
}