package com.kabunov.showcase.data.di

import com.kabunov.showcase.data.repository.ContentRepository
import com.kabunov.showcase.data.repository.ContentRepositoryDb
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsContentRepository(
        contentRepository: ContentRepositoryDb,
    ): ContentRepository

}
