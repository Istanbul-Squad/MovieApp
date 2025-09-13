package com.karrar.movieapp.ui.profile

sealed interface ProfileUIEvent {
    object LoginEvent : ProfileUIEvent
    object RatedMoviesEvent : ProfileUIEvent
    object CollectionsEvent: ProfileUIEvent
    object DialogLogoutEvent : ProfileUIEvent
    object WatchHistoryEvent : ProfileUIEvent
}
