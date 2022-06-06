package com.gonexwind.nexthotel

import android.app.SearchManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.gonexwind.nexthotel.adapter.HotelVerticalAdapter
import com.gonexwind.nexthotel.databinding.ActivityMainBinding
import com.gonexwind.nexthotel.model.Hotel
import com.gonexwind.nexthotel.repository.SearchRepository
import com.gonexwind.nexthotel.ui.explore.ExploreFragmentDirections
import com.gonexwind.nexthotel.ui.home.HomeFragmentDirections
import com.gonexwind.nexthotel.viewmodel.MainViewModel
import com.gonexwind.nexthotel.viewmodel.MainViewModelProviderFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: HotelVerticalAdapter

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
    private var searchJob: Job? = null
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        bindNavView()
        observeViewState()
        bindSearchView()
        setUpRecyclerView()
    }

    private fun init() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val searchRepository = SearchRepository()
        val viewModelProviderFactory =
            MainViewModelProviderFactory(application, searchRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[MainViewModel::class.java]

    }


    private fun setUpRecyclerView() {
        viewModel.searchResult.observe(this) {
            it?.let {
                adapter = HotelVerticalAdapter(it)
                binding.searchRecyclerView.adapter = adapter

                adapter.setOnItemClickCallback(object :
                    HotelVerticalAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: Hotel) {
                        when (navController.currentDestination?.id) {
                            R.id.navigation_explore -> {
                                val toDetail =
                                    ExploreFragmentDirections.actionNavigationExploreToDetailFragment(
                                        data
                                    )
                                navController.navigate(toDetail)
                            }
                            R.id.navigation_home -> {
                                val toDetail =
                                    HomeFragmentDirections.actionNavigationHomeToDetailFragment(data)
                                navController.navigate(toDetail)
                            }
                        }
                        showInitialScreen()
                    }
                })
            }
        }
    }

    private fun bindNavView() {
        val navView: BottomNavigationView = binding.navView
        navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.detailFragment -> {
                    navView.visibility = View.GONE
                    binding.topBar.visibility = View.GONE
                }
                R.id.navigation_bookmarks -> {
                    binding.topBar.visibility = View.GONE
                }
                else -> {
                    navView.visibility = View.VISIBLE
                    binding.topBar.visibility = View.VISIBLE
                }
            }
        }

    }

    /** binding the search view */
    private fun bindSearchView() {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = binding.searchView
        searchView.setBackgroundColor(Color.WHITE)
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        binding.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            /** delay the search with 400 milliseconds when the last character is typed */
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onQueryTextChange(newText: String): Boolean {
                searchJob?.cancel()
                searchJob = coroutineScope.launch {
                    newText.let {
                        delay(400)
                        if (it.isNotEmpty()) {
                            viewModel.searchForTasks(newText)
                        } else {
                            viewModel.clearSearchResult()
                        }
                    }
                }
                return false
            }
        })
    }

    /**  when the viewState changes, also we change what we show into the view, loading, result or init screen */
    private fun observeViewState() {
        viewModel.viewState.observe(this@MainActivity) { searchViewState ->
            when (searchViewState) {
                MainViewModel.SearchViewState.LOADING.ordinal -> {
                    showSearchingLoading()
                }

                MainViewModel.SearchViewState.SEARCH_RESULT.ordinal -> {
                    showSearchResult()
                }

                MainViewModel.SearchViewState.INITIAL_SCREEN.ordinal -> {
                    showInitialScreen()
                }

                MainViewModel.SearchViewState.ERROR.ordinal -> {
                    showAnErrorHappened()
                }

                MainViewModel.SearchViewState.RESULT_NOT_FOUND.ordinal -> {
                    showNoResultFound()
                }
            }
        }
    }

    /** showing the initial screen */
    private fun showInitialScreen() {
        binding.searchResult.visibility = View.INVISIBLE
        binding.searchProgressBar.visibility = View.INVISIBLE
        binding.searchRecyclerView.visibility = View.INVISIBLE
    }

    /** showing the searching loading screen */
    private fun showSearchingLoading() {
        binding.searchResult.visibility = View.VISIBLE
        binding.searchProgressBar.visibility = View.VISIBLE
        binding.searchRecyclerView.visibility = View.INVISIBLE
    }

    /** showing the result found screen */
    private fun showSearchResult() {
        binding.searchResult.visibility = View.VISIBLE
        binding.searchProgressBar.visibility = View.INVISIBLE
        binding.searchRecyclerView.visibility = View.VISIBLE
    }

    /** showing the result not found screen */
    private fun showNoResultFound() {
        binding.searchResult.visibility = View.VISIBLE
        binding.searchProgressBar.visibility = View.INVISIBLE
        binding.searchRecyclerView.visibility = View.INVISIBLE
        Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show()
    }

    /** showing the error result screen */
    private fun showAnErrorHappened() {
        binding.searchResult.visibility = View.INVISIBLE
        binding.searchProgressBar.visibility = View.INVISIBLE
        binding.searchRecyclerView.visibility = View.INVISIBLE
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
    }

}