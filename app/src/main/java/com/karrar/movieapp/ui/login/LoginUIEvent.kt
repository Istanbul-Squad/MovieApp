package com.karrar.movieapp.ui.login

sealed interface LoginUIEvent {
    data class LoginEvent(val login: Int) : LoginUIEvent
    object SignUpEvent : LoginUIEvent
    object GuestEvent : LoginUIEvent
    object ForgetPasswordEvent : LoginUIEvent
}