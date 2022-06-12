package com.nexthotel.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nexthotel.R
import com.nexthotel.core.data.Result
import com.nexthotel.core.ui.ViewModelFactory
import com.nexthotel.databinding.ActivityMainBinding
import com.nexthotel.ui.home.BestPickAdapter
import com.nexthotel.ui.search.SearchViewModel
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var adapter: BestPickAdapter
    lateinit var viewModel: SearchViewModel
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
    private var searchJob: Job? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: SearchViewModel by viewModels { factory }
        this.viewModel = viewModel

        bottomNavigation()
        searchView()
    }

    private fun searchView() {
        adapter = BestPickAdapter {
            if (it.isBookmarked) viewModel.deleteHotel(it) else viewModel.saveHotel(it)
        }

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = binding.searchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(q: String?): Boolean {
                observeViewState(q ?: "")
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                searchJob?.cancel()
                searchJob = coroutineScope.launch {
                    p0.let {
                        delay(400)
                        observeViewState(it ?: "")
                    }
                }
                return true
            }
        })
    }

    private fun bottomNavigation() {
        val navView: BottomNavigationView = binding.navView
        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController

        navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.detailFragment -> {
                    navView.visibility = View.GONE
                    binding.topBar.visibility = View.GONE
                }
                R.id.navigation_bookmarks -> binding.topBar.visibility = View.GONE
                else -> {
                    navView.visibility = View.VISIBLE
                    binding.topBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun observeViewState(q: String) {
        viewModel.searchHotel(q).observe(this@MainActivity) {
            if (it != null) {
                when (it) {
                    is Result.Loading -> showSearchingLoading()
                    is Result.Success -> {
                        val hotelData = it.data
                        adapter.submitList(hotelData)
                        binding.searchRecyclerView.adapter = adapter
                        showSearchResult()
                    }
                    is Result.NotFound -> showNoResultFound()
                    is Result.Error -> showAnErrorHappened()
                }
            }
        }
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