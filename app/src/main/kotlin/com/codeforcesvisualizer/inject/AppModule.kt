package com.codeforcesvisualizer.inject

import com.codeforcesvisualizer.data.datasource.CFRemoteDataSource
import com.codeforcesvisualizer.data.datasource.CFRemoteDataSourceImpl
import com.codeforcesvisualizer.data.repository.CFRepositoryImpl
import com.codeforcesvisualizer.domain.repository.CFRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
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
    abstract fun bindCFRemoteDataSource(dataSourceImpl: CFRemoteDataSourceImpl): CFRemoteDataSource
}