package com.karrar.movieapp.domain.usecases.home.getData

import com.karrar.movieapp.data.repository.AccountRepository
import com.karrar.movieapp.domain.models.User
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {
    suspend operator fun invoke(): User {
        return User(
            userImage = "",
            name = accountRepository.getAccountDetails()?.name ?: "",
            userName = accountRepository.getAccountDetails()?.username ?: "",
            rating = 0f
        )
    }
}