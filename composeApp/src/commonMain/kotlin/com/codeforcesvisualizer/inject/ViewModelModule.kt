package com.codeforcesvisualizer.inject

import com.codeforcesvisualizer.compare.CompareHandlesViewModel
import com.codeforcesvisualizer.contest.details.ContestDetailsViewModel
import com.codeforcesvisualizer.contest.list.ContestViewModel
import com.codeforcesvisualizer.contest.search.ContestSearchViewModel
import com.codeforcesvisualizer.preference.PreferenceViewModel
import com.codeforcesvisualizer.preference.ThemeManager
import com.codeforcesvisualizer.preference.ThemeManagerViewModel
import com.codeforcesvisualizer.profile.ProfileSearchViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { ContestViewModel(get()) }
    factory { ContestSearchViewModel(get()) }
    factory { ContestDetailsViewModel(get()) }
    factory { ProfileSearchViewModel(get(), get(), get()) }
    factory { CompareHandlesViewModel(get(), get()) }
    factory<ThemeManager> { ThemeManagerViewModel(get(), get()) }
    factory { PreferenceViewModel() }
}
