package com.karrar.movieapp.ui.profile.myratings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.karrar.movieapp.R
import com.karrar.movieapp.databinding.FragmentMyRatingsBinding
import com.karrar.movieapp.ui.base.BaseFragment
import com.karrar.movieapp.utilities.collectLast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyRatingsFragment : BaseFragment<FragmentMyRatingsBinding>() {
    override val layoutIdFragment: Int = R.layout.fragment_my_ratings
    override val viewModel: MyRatingsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(true, getString(R.string.my_ratings))

        setupRecyclerView()
        setupTabs()
        observeEvents()
    }

    private fun setupRecyclerView() {
        val adapter = RatedMoviesAdapter(emptyList(), viewModel)
        binding.recyclerViewRatedMovies.adapter = adapter
    }

    private fun setupTabs() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let { viewModel.onTabSelected(it.position) }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun observeEvents() {
        collectLast(viewModel.myRatingUIEvent) {
            it.getContentIfNotHandled()?.let { onEvent(it) }
        }
    }

    private fun onEvent(event: MyRatingUIEvent) {
        val action = when (event) {
            is MyRatingUIEvent.MovieEvent -> {
                MyRatingsFragmentDirections.actionRatedMoviesFragmentToMovieDetailFragment(event.movieID)
            }

            is MyRatingUIEvent.TVShowEvent -> {
                MyRatingsFragmentDirections.actionRatedMoviesFragmentToTvShowDetailsFragment(event.tvShowID)
            }
        }
        findNavController().navigate(action)
    }
}