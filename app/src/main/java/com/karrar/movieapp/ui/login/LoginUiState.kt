package com.karrar.movieapp.ui.login

data class LoginUiState(
    val userName: String = "",
    val password: String = "",
    val showPassword: Boolean = false,
    val userNameHelperText: String = "",
    val passwordHelperText: String = "",
    val isLoading: Boolean = false,
    val isValidForm: Boolean = false,
    val error: String = "",
    )