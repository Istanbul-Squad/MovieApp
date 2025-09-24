package com.karrar.movieapp.domain.models

import kotlinx.datetime.LocalDate

data class Review(
    val content: String,
    val createDate: LocalDate,
    val userImage: String,
    val name: String,
    val userName: String,
    val rating: Float
)
