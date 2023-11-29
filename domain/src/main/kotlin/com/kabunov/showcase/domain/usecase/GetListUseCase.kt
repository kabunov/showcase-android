package com.kabunov.showcase.domain.usecase

import com.kabunov.showcase.model.IrregularVerb
import com.kabunov.showcase.data.repository.ContentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetListUseCase @Inject constructor(private val contentRepository: ContentRepository) {

    operator fun invoke(): Flow<List<IrregularVerb>> {
        return contentRepository.getIrregularVerbs()
    }
}