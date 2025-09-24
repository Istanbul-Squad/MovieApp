package com.karrar.movieapp.ui.actorDetails

sealed interface ActorDetailsUIEvent {
    object BackEvent : ActorDetailsUIEvent
    object SeeAllMovies : ActorDetailsUIEvent

    data class ClickMovieEvent(val movieID: Int) : ActorDetailsUIEvent
    object SeeMoreGallery:ActorDetailsUIEvent
    data class OpenSocialMediaLinkEvent(val link: String) : ActorDetailsUIEvent

}