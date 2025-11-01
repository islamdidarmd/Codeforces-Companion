package com.codeforcesvisualizer.inject

import com.codeforcesvisualizer.shared.data.datasource.CFRemoteDataSource
import com.codeforcesvisualizer.shared.data.datasource.CFRemoteDataSourceImpl
import com.codeforcesvisualizer.shared.data.repository.CFRepositoryImpl
import com.codeforcesvisualizer.shared.data.repository.ThemeRepositoryImpl
import com.codeforcesvisualizer.shared.domain.repository.CFRepository
import com.codeforcesvisualizer.shared.domain.repository.ThemeRepository
import org.koin.dsl.module

val appModule = module {
    single<CFRemoteDataSource> {
        CFRemoteDataSourceImpl(
            get()
        )
    }
    single<CFRepository> { CFRepositoryImpl(get()) }
    single<ThemeRepository> { ThemeRepositoryImpl() }
}