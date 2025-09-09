package com.karrar.movieapp.ui.profile.myratings

import androidx.lifecycle.viewModelScope
import com.karrar.movieapp.domain.models.Rated
import com.karrar.movieapp.domain.usecases.GetListOfRatedUseCase
import com.karrar.movieapp.domain.usecases.movieDetails.GetMovieDetailsUseCase
import com.karrar.movieapp.ui.base.BaseViewModel
import com.karrar.movieapp.utilities.Constants
import com.karrar.movieapp.utilities.Event
import com.karrar.movieapp.utilities.TMDBGenreConverter
import com.karrar.movieapp.utilities.formatDuration
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.map

@HiltViewModel
class MyRatingsViewModel @Inject constructor(
    private val getRatedUseCase: GetListOfRatedUseCase,
    private val ratedUIStateMapper: RatedUIStateMapper,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : BaseViewModel(), RatedMoviesInteractionListener {

    private val _ratedUiState = MutableStateFlow(MyRateUIState())
    val ratedUiState: StateFlow<MyRateUIState> = _ratedUiState

    private val _myRatingUIEvent: MutableStateFlow<Event<MyRatingUIEvent?>> =
        MutableStateFlow(Event(null))
    val myRatingUIEvent = _myRatingUIEvent.asStateFlow()

    private val _selectedTab = MutableStateFlow(0)

    init {
        getData()
    }

    override fun getData() {
        viewModelScope.launch {
            _ratedUiState.update { it.copy(isLoading = true) }
            try {
                val ratedItems = fetchRatedItems()
                val ratedItemsWithDetails = addMovieDetailsData(ratedItems) // Combined function
                val uiStateList = mapToUIState(ratedItemsWithDetails)

                updateRatedList(uiStateList)
                filterByCurrentTab()
            } catch (e: Throwable) {
                handleError()
            }
        }
    }

    private suspend fun fetchRatedItems() = getRatedUseCase()

    private suspend fun addMovieDetailsData(ratedItems: List<Rated>): List<Rated> {
        return ratedItems.map { rate ->
            if (rate.mediaType == Constants.MOVIE) {
                val movieDetails = getMovieDetailsUseCase.getMovieDetails(rate.id)
                rate.copy(
                    duration = formatDuration(movieDetails.movieDuration),
                )
            } else {
                rate
            }
        }
    }

    private fun mapToUIState(ratedItems: List<Rated>): List<RatedUIState> {
        return ratedItems.map { rate ->
            ratedUIStateMapper.map(input = rate)
        }
    }

    private fun updateRatedList(uiStateList: List<RatedUIState>) {
        _ratedUiState.update {
            it.copy(
                ratedList = uiStateList,
                isLoading = false
            )
        }
    }

    private fun filterByCurrentTab() {
        filterByTab(_selectedTab.value)
    }

    private fun handleError() {
        _ratedUiState.update {
            it.copy(
                error = listOf(Error("")),
                isLoading = false
            )
        }
    }

    fun onTabSelected(position: Int) {
        _selectedTab.update { position }
        filterByTab(position)
    }

    private fun filterByTab(tabPosition: Int) {
        val allItems = _ratedUiState.value.ratedList
        val filteredItems = when (tabPosition) {
            0 -> allItems.filter { it.mediaType == Constants.MOVIE }
            1 -> allItems.filter { it.mediaType == Constants.TV_SHOWS }
            else -> allItems
        }

        _ratedUiState.update {
            it.copy(filteredRatedList = filteredItems)
        }
    }

    override fun onClickMovie(movieId: Int) {
        ratedUiState.value.ratedList.let { it ->
            val item = it.find { it.id == movieId }
            item?.let {
                if (it.mediaType == Constants.MOVIE) {
                    _myRatingUIEvent.update { Event(MyRatingUIEvent.MovieEvent(movieId)) }
                } else {
                    _myRatingUIEvent.update { Event(MyRatingUIEvent.TVShowEvent(movieId)) }
                }
            }
        }
    }

    fun retryConnect() {
        _ratedUiState.update { it.copy(error = emptyList()) }
        getData()
    }
}