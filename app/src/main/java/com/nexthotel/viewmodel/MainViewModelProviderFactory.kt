package com.gonexwind.nexthotel.viewmodel

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gonexwind.nexthotel.repository.SearchRepository

class MainViewModelProviderFactory(
    private val app: Application,
    private val searchRepository: SearchRepository
) : ViewModelProvider.Factory {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(app, searchRepository) as T
    }

}