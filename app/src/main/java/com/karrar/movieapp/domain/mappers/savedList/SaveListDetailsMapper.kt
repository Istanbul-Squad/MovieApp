package com.karrar.movieapp.domain.mappers.savedList

import com.karrar.movieapp.BuildConfig
import com.karrar.movieapp.data.remote.response.SavedListDto
import com.karrar.movieapp.domain.mappers.Mapper
import com.karrar.movieapp.domain.models.SaveListDetails
import com.karrar.movieapp.utilities.TMDBGenreConverter
import javax.inject.Inject

class SaveListDetailsMapper @Inject constructor() : Mapper<SavedListDto, SaveListDetails> {
    override fun map(input: SavedListDto): SaveListDetails {
        return SaveListDetails(
            id = input.id ?: 0,
            mediaType = input.mediaType ?: "",
            title = listOf(input.originalTitle, input.originalName).filter { it != null }.first()
                .toString(),
            releaseDate = listOf(input.firstAirDate, input.releaseDate).filter { it != null }
                .first().toString(),
            voteAverage = input.voteAverage ?: 0.0,
            posterPath = BuildConfig.IMAGE_BASE_PATH + input.backdropPath,
            duration = input.runtime ?: 0,
            genres = TMDBGenreConverter.genreIdsToString(input.genreIds?.map { it ?: -1 }
                ?: emptyList())
        )
    }
}