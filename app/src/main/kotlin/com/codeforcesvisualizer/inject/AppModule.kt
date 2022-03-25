package com.codeforcesvisualizer.inject

import com.codeforcesvisualizer.data.datasource.CFRemoteDataSource
import com.codeforcesvisualizer.data.datasource.CFRemoteDataSourceImpl
import com.codeforcesvisualizer.data.repository.CFRepositoryImpl
import com.codeforcesvisualizer.data.repository.ThemeRepositoryImpl
import com.codeforcesvisualizer.domain.repository.CFRepository
import com.codeforcesvisualizer.domain.repository.ThemeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun bindCFRepository(repositoryImpl: CFRepositoryImpl): CFRepository

    @Binds
    @Singleton
    abstract fun bindThemeRepository(themeRepository: ThemeRepositoryImpl): ThemeRepository

    @Binds
    @Singleton
    abstract fun bindCFRemoteDataSource(dataSourceImpl: CFRemoteDataSourceImpl): CFRemoteDataSource
}