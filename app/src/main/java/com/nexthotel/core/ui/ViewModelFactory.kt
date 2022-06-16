package com.nexthotel.core.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nexthotel.core.data.HotelRepository
import com.nexthotel.core.di.Injection
import com.nexthotel.ui.bookmarks.BookmarkViewModel
import com.nexthotel.ui.detail.DetailViewModel
import com.nexthotel.ui.explore.ExploreViewModel
import com.nexthotel.ui.home.HomeViewModel
import com.nexthotel.ui.search.SearchViewModel

class ViewModelFactory private constructor(private val hotelRepository: HotelRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> SearchViewModel(hotelRepository) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(hotelRepository) as T
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> DetailViewModel(hotelRepository) as T
            modelClass.isAssignableFrom(ExploreViewModel::class.java) -> ExploreViewModel(hotelRepository) as T
            modelClass.isAssignableFrom(BookmarkViewModel::class.java) -> BookmarkViewModel(hotelRepository) as T
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