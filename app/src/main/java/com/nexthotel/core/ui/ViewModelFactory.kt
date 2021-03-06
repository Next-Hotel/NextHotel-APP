package com.nexthotel.core.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nexthotel.core.data.HotelRepository
import com.nexthotel.core.di.Injection
import com.nexthotel.ui.explore.ExploreViewModel
import com.nexthotel.ui.home.HomeViewModel
import com.nexthotel.ui.search.SearchViewModel
import com.nexthotel.ui.survey.SurveyViewModel

class ViewModelFactory private constructor(private val repo: HotelRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> SearchViewModel(repo) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(repo) as T
            modelClass.isAssignableFrom(ExploreViewModel::class.java) -> ExploreViewModel(repo) as T
            modelClass.isAssignableFrom(SurveyViewModel::class.java) -> SurveyViewModel(repo) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
        }
    }
}