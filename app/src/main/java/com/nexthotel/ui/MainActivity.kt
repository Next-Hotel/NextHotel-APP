package com.nexthotel.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nexthotel.R
import com.nexthotel.core.data.local.entity.HotelEntity
import com.nexthotel.core.ui.ViewModelFactory
import com.nexthotel.databinding.ActivityMainBinding
import com.nexthotel.ui.explore.ExploreFragmentDirections
import com.nexthotel.ui.home.HomeFragmentDirections
import com.nexthotel.ui.search.SearchAdapter
import com.nexthotel.ui.search.SearchViewModel
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var adapter: SearchAdapter
    lateinit var viewModel: SearchViewModel
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
    private var searchJob: Job? = null
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: SearchViewModel by viewModels { factory }
        this.viewModel = viewModel

        setUpRecyclerView()
        observeViewState()
        bottomNavigation()
        searchView()
        setUpAction()
    }

    private fun setUpAction() {
        binding.backToHomeButton.setOnClickListener {
            showInitialScreen()
        }
        binding.settingButton.setOnClickListener {
            val toSetting =
                HomeFragmentDirections.actionNavigationHomeToSettingFragment()
            navController.navigate(toSetting)
        }
    }

    private fun searchView() {
        adapter = SearchAdapter {
            if (it.isBookmarked) viewModel.deleteHotel(it) else viewModel.saveHotel(it)
        }

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = binding.searchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(q: String?): Boolean {
                viewModel.searchForTasks(q ?: "")
                searchView.clearFocus()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                searchJob?.cancel()
                searchJob = coroutineScope.launch {
                    p0.let {
                        delay(400)
                        if (it?.isNotEmpty() == true) {
                            viewModel.searchForTasks(p0 ?: "")
                        } else {
                            viewModel.clearSearchResult()
                        }
                    }
                }
                return false
            }
        })
    }

    private fun bottomNavigation() {
        val navView: BottomNavigationView = binding.navView
        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController

        navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.detailFragment -> {
                    navView.visibility = View.GONE
                    binding.topBar.visibility = View.GONE
                }
                R.id.navigation_bookmarks -> binding.topBar.visibility = View.GONE
                R.id.settingFragment -> binding.topBar.visibility = View.GONE
                else -> {
                    navView.visibility = View.VISIBLE
                    binding.topBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun observeViewState() {
        viewModel.viewState.observe(this@MainActivity) { searchViewState ->
            when (searchViewState) {
                SearchViewModel.SearchViewState.LOADING.ordinal -> {
                    showSearchingLoading()
                }

                SearchViewModel.SearchViewState.SEARCH_RESULT.ordinal -> {
                    showSearchResult()
                }

                SearchViewModel.SearchViewState.INITIAL_SCREEN.ordinal -> {
                    showInitialScreen()
                }

                SearchViewModel.SearchViewState.ERROR.ordinal -> {
                    showAnErrorHappened()
                }

                SearchViewModel.SearchViewState.RESULT_NOT_FOUND.ordinal -> {
                    showNoResultFound()
                }
            }
        }
    }


    private fun setUpRecyclerView() {
        viewModel.searchResult.observe(this) {
            it?.let {
                val hotelData = it
                adapter.submitList(hotelData)
                binding.searchRecyclerView.adapter = adapter
                adapter.setOnItemClickCallback(object :
                    SearchAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: HotelEntity) {
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
                showSearchResult()
            }
        }
    }

    private fun showInitialScreen() {
        binding.searchResult.visibility = View.INVISIBLE
        binding.searchProgressBar.visibility = View.INVISIBLE
        binding.searchRecyclerView.visibility = View.INVISIBLE
        binding.otherResult.visibility = View.INVISIBLE
    }

    private fun showSearchingLoading() {
        binding.searchResult.visibility = View.VISIBLE
        binding.searchProgressBar.visibility = View.VISIBLE
        binding.searchRecyclerView.visibility = View.INVISIBLE
        binding.otherResult.visibility = View.INVISIBLE
    }

    private fun showSearchResult() {
        binding.searchResult.visibility = View.VISIBLE
        binding.searchProgressBar.visibility = View.INVISIBLE
        binding.searchRecyclerView.visibility = View.VISIBLE
        binding.otherResult.visibility = View.INVISIBLE
    }

    private fun showNoResultFound() {
        binding.searchResult.visibility = View.GONE
        binding.searchProgressBar.visibility = View.INVISIBLE
        binding.searchRecyclerView.visibility = View.INVISIBLE
        binding.otherResult.visibility = View.VISIBLE
        binding.imageOtherResult.setImageResource(R.drawable.ic_searching_not_found)
    }

    private fun showAnErrorHappened() {
        binding.searchResult.visibility = View.INVISIBLE
        binding.searchProgressBar.visibility = View.INVISIBLE
        binding.searchRecyclerView.visibility = View.INVISIBLE
        binding.otherResult.visibility = View.VISIBLE
        binding.imageOtherResult.setImageResource(R.drawable.ic_searching_error)
    }
}