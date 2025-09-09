package com.karrar.movieapp.ui.profile.myratings

import com.karrar.movieapp.domain.mappers.Mapper
import com.karrar.movieapp.domain.models.Rated
import javax.inject.Inject

class RatedUIStateMapper @Inject constructor() : Mapper<Rated, RatedUIState> {
    override fun map(input: Rated): RatedUIState {
        return RatedUIState(
            id = input.id,
            title = input.title,
            posterPath = input.posterPath,
            rating = input.rating,
            mediaType = input.mediaType,
            releaseDate = formatDate(input.releaseDate),
            duration = input.duration,
            genres = input.genres
        )
    }

    private fun formatDate(date: String?) = date
        ?.replace("-", " ")
        ?.split(" ")
        ?.run {
            "${this[0]}, ${
                listOf(
                    "Jan",
                    "Feb",
                    "Mar",
                    "Apr",
                    "May",
                    "Jun",
                    "Jul",
                    "Aug",
                    "Sep",
                    "Oct",
                    "Nov",
                    "Dec"
                )[this[1].toInt()]
            } ${this[2]}"
        } ?: "Unknown"
}