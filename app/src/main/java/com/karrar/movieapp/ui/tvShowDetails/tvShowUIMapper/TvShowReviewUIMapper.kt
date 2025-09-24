package com.karrar.movieapp.ui.tvShowDetails.tvShowUIMapper

import com.karrar.movieapp.domain.mappers.Mapper
import com.karrar.movieapp.domain.models.Review
import com.karrar.movieapp.ui.tvShowDetails.tvShowUIState.ReviewUIState
import javax.inject.Inject

class TvShowReviewUIMapper @Inject constructor() : Mapper<Review, ReviewUIState> {
    override fun map(input: Review): ReviewUIState {

        val date = input.createDate
        val month = date.month.name.take(3).lowercase()
            .replaceFirstChar { it.uppercase() }

        val formattedDate = "$month ${date.day}, ${date.year}"
        return ReviewUIState(
            content = input.content,
            createDate = formattedDate,
            userImage = input.userImage,
            name = input.name,
            userName = input.userName,
            rating = input.rating
        )
    }
}
