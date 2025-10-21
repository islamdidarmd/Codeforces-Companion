package com.codeforcesvisualizer.inject

import com.codeforcesvisualizer.compare.CompareHandlesViewModel
import com.codeforcesvisualizer.contest.details.ContestDetailsViewModel
import com.codeforcesvisualizer.contest.list.ContestViewModel
import com.codeforcesvisualizer.contest.search.ContestSearchViewModel
import com.codeforcesvisualizer.preference.PreferenceViewModel
import com.codeforcesvisualizer.preference.ThemeManagerViewModel
import com.codeforcesvisualizer.profile.ProfileSearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ContestViewModel(get()) }
    viewModel { ContestSearchViewModel(get()) }
    viewModel { ContestDetailsViewModel(get()) }
    viewModel { ProfileSearchViewModel(get(), get(), get()) }
    viewModel { CompareHandlesViewModel(get(), get()) }
    viewModel { ThemeManagerViewModel(get(), get()) }
    viewModel { PreferenceViewModel() }
}
