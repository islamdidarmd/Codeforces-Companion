package com.codeforcesvisualizer.inject

import com.codeforcesvisualizer.data.datasource.CFRemoteDataSource
import com.codeforcesvisualizer.data.datasource.CFRemoteDataSourceImpl
import com.codeforcesvisualizer.data.repository.CFRepositoryImpl
import com.codeforcesvisualizer.data.repository.ThemeRepositoryImpl
import com.codeforcesvisualizer.domain.repository.CFRepository
import com.codeforcesvisualizer.domain.repository.ThemeRepository
import org.koin.dsl.module

val appModule = module {
    single<CFRemoteDataSource> { CFRemoteDataSourceImpl(get()) }
    single<CFRepository> { CFRepositoryImpl(get()) }
    single<ThemeRepository> { ThemeRepositoryImpl() }
}