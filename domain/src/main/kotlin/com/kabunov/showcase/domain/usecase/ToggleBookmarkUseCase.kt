package com.kabunov.showcase.domain.usecase

import com.kabunov.showcase.data.repository.ContentRepository
import javax.inject.Inject

class ToggleBookmarkUseCase @Inject constructor(private val contentRepository: ContentRepository) {

    suspend operator fun invoke(id: String, bookmarked:Boolean): Int {
        return contentRepository.toggleBookmark(id, bookmarked)
    }
}