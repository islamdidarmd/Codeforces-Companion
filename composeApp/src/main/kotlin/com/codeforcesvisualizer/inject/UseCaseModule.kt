package com.codeforcesvisualizer.inject

import com.codeforcesvisualizer.shared.domain.usecase.FilterContestListUseCase
import com.codeforcesvisualizer.shared.domain.usecase.GetContestByIdUseCase
import com.codeforcesvisualizer.shared.domain.usecase.GetContestListUseCase
import com.codeforcesvisualizer.shared.domain.usecase.GetUiThemeModeUseCase
import com.codeforcesvisualizer.shared.domain.usecase.GetUserInfoByHandleUseCase
import com.codeforcesvisualizer.shared.domain.usecase.GetUserRatingsByHandleUseCase
import com.codeforcesvisualizer.shared.domain.usecase.GetUserStatusByHandleUseCase
import com.codeforcesvisualizer.shared.domain.usecase.SetUiThemeModeUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetContestListUseCase(get()) }
    single { GetContestByIdUseCase(get()) }
    single { FilterContestListUseCase(get()) }
    single { GetUserInfoByHandleUseCase(get()) }
    single { GetUserStatusByHandleUseCase(get()) }
    single { GetUserRatingsByHandleUseCase(get()) }
    single { GetUiThemeModeUseCase(get(), get()) }
    single { SetUiThemeModeUseCase(get(), get()) }
}
