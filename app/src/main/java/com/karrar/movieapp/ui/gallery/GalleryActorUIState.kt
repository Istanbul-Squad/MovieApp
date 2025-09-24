package com.karrar.movieapp.ui.gallery

import com.karrar.movieapp.ui.actorDetails.Error


data class GalleryActorUIState(
    val name: String = "",
    val imagesUrl: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: List<Error> = emptyList(),
)