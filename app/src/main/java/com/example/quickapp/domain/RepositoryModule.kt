package com.example.quickapp.domain

import com.example.quickapp.domain.repo.PostRepository
import com.example.quickapp.domain.repo.PostRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePostRepository(
        api: PostApi
    ): PostRepository {
        return PostRepositoryImpl(api)
    }
}