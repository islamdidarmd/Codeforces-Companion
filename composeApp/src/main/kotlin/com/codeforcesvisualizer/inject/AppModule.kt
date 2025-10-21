package com.codeforcesvisualizer.inject

import com.codeforcesvisualizer.shared.data.datasource.CFRemoteDataSource
import com.codeforcesvisualizer.shared.data.datasource.CFRemoteDataSourceImpl
import com.codeforcesvisualizer.shared.data.repository.CFRepositoryImpl
import com.codeforcesvisualizer.shared.data.repository.ThemeRepositoryImpl
import com.codeforcesvisualizer.shared.domain.repository.CFRepository
import com.codeforcesvisualizer.shared.domain.repository.ThemeRepository
import org.koin.dsl.module

val appModule = module {
    single<com.codeforcesvisualizer.shared.data.datasource.CFRemoteDataSource> {
        _root_ide_package_.com.codeforcesvisualizer.shared.data.datasource.CFRemoteDataSourceImpl(
            get()
        )
    }
    single<CFRepository> { _root_ide_package_.com.codeforcesvisualizer.shared.data.repository.CFRepositoryImpl(get()) }
    single<ThemeRepository> { _root_ide_package_.com.codeforcesvisualizer.shared.data.repository.ThemeRepositoryImpl() }
}