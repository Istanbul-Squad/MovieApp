package com.karrar.movieapp.domain.mappers

import com.karrar.movieapp.data.remote.response.review.ReviewsDto
import com.karrar.movieapp.domain.models.Review
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class ReviewMapper @Inject constructor() : Mapper<ReviewsDto, Review> {
    @OptIn(ExperimentalTime::class)
    override fun map(input: ReviewsDto): Review {
        val date = input.createdAt?.let {
            Instant.parse(it).toLocalDateTime(TimeZone.UTC).date
        } ?: LocalDate(1970, 1, 1)

        return Review(
            content = input.content ?: "",
            createDate = date,
            userImage = input.authorDetails?.avatarPath ?: "",
            name = input.authorDetails?.name ?: "GUEST",
            userName = input.authorDetails?.username ?: "GUEST",
            rating = input.authorDetails?.rating?.toFloat() ?: 0f
        )
    }
}