package com.learnbyheart.core.data.di

import com.learnbyheart.core.data.repository.home.HomeDataRepository
import com.learnbyheart.core.data.repository.home.HomeDataRepositoryImpl
import com.learnbyheart.core.data.repository.search.SearchByTypeRepository
import com.learnbyheart.core.data.repository.search.SearchByTypeRepositoryImpl
import com.learnbyheart.core.data.repository.search.SearchRepository
import com.learnbyheart.core.data.repository.search.SearchRepositoryImpl
import com.learnbyheart.core.data.repository.token.TokenRepository
import com.learnbyheart.core.data.repository.token.TokenRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTokenRepository(tokenRepositoryImpl: TokenRepositoryImpl): TokenRepository

    @Binds
    @Singleton
    abstract fun bindHomeDataRepository(homeDataRepositoryImpl: HomeDataRepositoryImpl): HomeDataRepository

    @Binds
    @Singleton
    abstract fun bindSearchRepository(searchRepositoryImpl: SearchRepositoryImpl): SearchRepository

    @Binds
    @Singleton
    abstract fun bindSearchByTypeRepository(searchByTypeRepositoryImpl: SearchByTypeRepositoryImpl): SearchByTypeRepository

}
